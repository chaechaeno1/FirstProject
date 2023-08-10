package service;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import dao.AdminDAO;
import util.ScanUtil;

public class AdminService {
	// 싱글톤 패턴
	private static AdminService instance = null;

	private AdminService() {
	}

	public static AdminService getInstance() {
		if (instance == null)
			instance = new AdminService();
		return instance;
	}

	// AdminDAO 호출 생성
	AdminDAO dao = AdminDAO.getInstance();

	// AdminService의 메인
	// 노선 -> 운행날짜 -> 운행시간 -> 버스 선택
	// 컨트롤러 -> 어드민페이지 -> 어드민 로그인 -> 노선 추가 / 삭제 기능

	// 로그인을 해주세요
	// 검증
	// 메뉴선택 - 노선추가/삭제

	public int admindControl() {
		System.out.println("관리자 페이지 입니다.");
		// 1. 로그인
		login();
		System.out.println("로그인에 성공했습니다.");
		
		// 2. 메뉴선택
		selectMenu();

		// 3. 노선 등록
		submitsv();
		// 4. 노선 수정
		
		// 5. 노선 삭제
		deletesv();

		return 1;
	}

	
	
	public int login() { //로그인 실패 코드짜기 (while-break)
		System.out.println("로그인 해주세요!");
		return 1;
	}

	public int selectMenu() {
		System.out.println("관리자메뉴를 선택해주세요.");
		System.out.println("1. 노선 저장 2. 노선 삭제");
		return 1;}

	public int deletesv() {
		//기등록된 운행정보 호출하여 리스트 출력
		System.out.println("운행정보 삭제");
		return 1;}
	
	
	
	

	public int submitsv() {

		System.out.println("** 1. 노선을 선택해주세요 (노선 ID를 입력해주세요) **");
		System.out.println();
		System.out.println("==============================================================================");
		System.out.println("노선ID\t\t노선명\t\t비용\t\t승차홈\t\t소요시간");
		System.out.println("==============================================================================");
		List<Map<String, Object>> routelist = dao.route();

		// 수정할 운행정보 선택하세요
		// 기등록된 운행정보 조회

		if (routelist == null) {
			System.out.println("등록된 버스 배차 목록이 없습니다.");
			System.out.println("===========================================================");
			System.out.println("**** 관리자 페이지 메인 ****");
			// todo 홈으로 이동
			return 1;

		} else {
			// 버스노선 리스트들이 출력됨
			for (Map<String, Object> item : routelist) {
				System.out.print(item.get("RT_ID"));
				System.out.print("\t\t" + item.get("RT_NAME"));
				System.out.print("\t\t" + item.get("RT_PRICE"));
				System.out.print("\t\t" + item.get("RT_PNUM"));
				System.out.print("\t\t" + item.get("RT_TIME"));
				System.out.println();

			}

			List<Object> param = new ArrayList<>();

			System.out.println("==============================================================================");
			while (true) {
				System.out.print("노선ID 입력: ");
				String rtIdInput = ScanUtil.nextLine().trim();

				boolean isInputValid = false;

				for (Map<String, Object> item : routelist) {
					String rtIdFromList = item.get("RT_ID").toString().trim(); // 노선ID가 문자열로 저장되어 있다고 가정
					if (rtIdInput.equals(rtIdFromList)) {
						isInputValid = true;
						break;
					}
				}

				if (!isInputValid) {
					System.out.println("노선ID 입력오류!!\n리스트에 있는 노선ID를 확인해주시기 바랍니다.");
					System.out.println("===========================================================");
				} else {
					System.out.println(">>선택된 노선ID : " + rtIdInput);
					param.add(rtIdInput);
					break;
				}

				System.out.println("==============================================================================");
				System.out.println();
				System.out.println("** 2. 운행날짜를 입력해주세요 (예시:20230810) **");
				String dateInput = "";
				while (true) {
					System.out.print("운행날짜 입력 : ");
					dateInput = ScanUtil.nextLine().trim();

					if (dateInput.length() != 8) {
						System.out.println("날짜 형식에 맞춰 다시 입력하세요. (예시:20230810)");
					} else {
						break;
					}
				}

				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
				LocalDate date = LocalDate.parse(dateInput, formatter);

				System.out.println("==============================================================================");
				System.out.println();
				System.out.println("** 3. 운행시간을 입력해주세요 (예시: 06:00) **");
				// 운행시간: 06:00 ~ 23:00로 제한
				getTime();

				// 시간값 참거짓 추가해서 오류 출력하고 while문 돌리기
//            while (true) {
				System.out.print("운행시간 입력 : ");
				String timeInput = ScanUtil.nextLine();

				LocalDateTime dateTime = LocalDateTime.of(date, LocalTime.parse(timeInput));
				Timestamp dt = Timestamp.valueOf(dateTime);

				param.add(dt);

				System.out.println("==============================================================================");

				System.out.println();
				System.out.println("** 4. 운행버스를 입력해주세요 (예시: b00001) **");
				System.out.println("==============================================================================");
				System.out.println("버스ID\t\t차량번호\t\t버스등급");
				System.out.println("==============================================================================");
				// DB에 등록된 BUS 테이블 조회
				List<Map<String, Object>> buslist = dao.buslist();

				for (Map<String, Object> item : buslist) {
					System.out.print(item.get("BUS_ID"));
					System.out.print("\t\t" + item.get("BUS_NUM"));
					System.out.print("\t\t" + item.get("BUS_GRADE"));
					System.out.println();
				}

				System.out.println("==============================================================================");
				while (true) {
					System.out.print("버스ID 입력 : ");
					String busInput = ScanUtil.nextLine().trim();

					boolean busInputValid = false;

					for (Map<String, Object> item : buslist) {
						String busIdFromList = item.get("BUS_ID").toString().trim(); // 버스ID가 문자열로 저장되어 있다고 가정
						if (busInput.equals(busIdFromList)) {
							busInputValid = true;
							break;
						}
					}

					if (!busInputValid) {
						System.out.println("버스ID 입력오류!!\n리스트에 있는 버스ID를 확인해주시기 바랍니다.");
						System.out.println("===========================================================");

					} else {
						System.out.println(">>선택된 버스ID : " + busInput);
						param.add(busInput);
						break;
					}

					// SV_ID 생성
					String svID = rtIdInput + dateTime.format(formatter);

					param.add(svID);

					System.out.println("********운행(SERVICE) 테이블에 등록된 데이터********");
					for (Object p : param) {
						System.out.println(p);
					}

					dao.insert(param);

				}

			}
		}
		return 0; // 임의의 값
	}

	// 시간설정 메소드
	private void getTime() {
		// 초기값 설정
		LocalTime time = LocalTime.of(6, 0);

		// 00시가 아닐때까지 시간 출력 6시~23시 30분까지
		while (!(time.equals(LocalTime.MIDNIGHT))) {
			System.out.print(time + " | ");
			time = time.plusHours(1);
		}
		System.out.println();
	}
}
