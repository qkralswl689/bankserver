# bankserver

#### ERD : https://dbdiagram.io/d/63fcb332296d97641d84088f
![스크린샷 2023-03-02 오후 9 51 04](https://user-images.githubusercontent.com/65608960/222433772-d9092f68-b5f9-435f-82fb-1042758b6dd9.png)
=> 1. Member 테이블이 메인이 되어 Friend(친구목록) & Account(계좌) & AccountDetail(이체내역) 의 user와 연관관계를 맺는다.
   2. AccountDetail 은 Account 의 Member / Sender & Account 가 연관관계를 맺는다.

#### 회원가입 api

- RDB 설계 : id /회원 email / 이름 / password 로 구성 
- ![스크린샷 2023-03-01 오후 8 51 04](https://user-images.githubusercontent.com/65608960/222131859-0a767727-1589-4958-a3de-92b9d4f8c797.png) 
- 테스트 코드 : 가입할 회원이 email & password & 이름 입력 -> 가입된 회원인지 확인 -> password PasswordEncoder 를 이용해 암호화 되어 DB에 저장.
- 개선하고 싶었던 부분 : 

#### 친구추가/삭제 / 친구 목록 조회 api
- 스펙 : 
- 아키텍처 스타일
- RDB 설계 : id / 회원 email / 친구인 회원의 email 로 구성 => Member 와 1:1로 매핑된다
- ![스크린샷 2023-03-01 오후 8 54 17](https://user-images.githubusercontent.com/65608960/222132690-6de0c3d6-b40a-468e-b5d8-c3c02f4003a2.png)
- 테스트 코드 
  1) 친구 추가 : user의 이메일과 추가할 user의 이메일을 입력 -> 입력된 user가 가입된 회원인지 확인 -> 친구로 이미 등록되어있는지 확인 -> 친구 추가 -> 양쪽다 친구로 등록됨
  2) 친구 삭제 : user의 이메일과 추가할 user의 이메일을 입력 입력된 user가 가입된 회원인지 확인 -> 친구로  등록되어있는지 확인 -> 친구목록에서 삭제 -> 양쪽다 친구목록에서 삭제됨
  3) 친구목록 조회 : 조회대상 user의 이메일을 입력 -> 입력된 user가 가입된 회원인지 확인 -> 대상user의 친구 목록을 조회
- 새롭게 알게된 부분
  1) 친구 추가 : 처음 친구등록 할때 한쪽만 친구로 등록하도록 코드를 구성해 추후 친구등록시 양쪽다 친구로 등록되도록 수정했다.


#### 계좌 api
- RDB 설계 :
  1) Account : 회원id & 계좌 /  잔액만 표시 
-  ![스크린샷 2023-03-01 오후 8 54 38](https://user-images.githubusercontent.com/65608960/222132699-93d333ac-1719-4fe6-9b1b-01c241408442.png)
  2) AccountDetail : 회원id & 계좌 / 보낸사람 / 입,출금 금액 / 입,출금 시간 / 잔액 표시 
- ![스크린샷 2023-03-01 오후 8 54 54](https://user-images.githubusercontent.com/65608960/222132703-bdbc8d87-a015-4b10-bc48-ebe8ab7cdc99.png)
- 테스트 코드 
  1) 계좌 생성 : user의 이메일 / 계좌번호 / 잔액 입력 -> 가입된 회원인지 확인 -> 입력된 계좌번호가 이미 존재하는지 확인 -> 계좌생성
  2) 계좌 이체 : 보낼 user의 이메일  & 계좌번호 / 받을 user의 이메일 & 계좌버호 입력 ->  가입된 회원인지 확인 -> 친구로 등록되어있는지 확인 -> 보낼 계좌의 잔액이 보낼금액보다 많은지 확인 -> 계좌이체 -> 알람 
  3) 계좌 조회 : 1) 조회할 user의 이메일 입력 ->  가입된 회원인지 확인 -> 해당 user의 생성된 모든 계좌의 계좌번호 / 잔액 조회
               2) 조회할 user의 이메일 & 계좌번호 입력 -> 가입된 회원인지 확인 -> 해당계좌의 입/출금 내역 조회


