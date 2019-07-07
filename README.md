# 실습을 위한 개발 환경 세팅
* https://github.com/slipp/web-application-server 프로젝트를 자신의 계정으로 Fork한다. Github 우측 상단의 Fork 버튼을 클릭하면 자신의 계정으로 Fork된다.
* Fork한 프로젝트를 eclipse 또는 터미널에서 clone 한다.
* Fork한 프로젝트를 eclipse로 import한 후에 Maven 빌드 도구를 활용해 eclipse 프로젝트로 변환한다.(mvn eclipse:clean eclipse:eclipse)
* 빌드가 성공하면 반드시 refresh(fn + f5)를 실행해야 한다.

# 웹 서버 시작 및 테스트
* webserver.WebServer 는 사용자의 요청을 받아 RequestHandler에 작업을 위임하는 클래스이다.
* 사용자 요청에 대한 모든 처리는 RequestHandler 클래스의 run() 메서드가 담당한다.
* WebServer를 실행한 후 브라우저에서 http://localhost:8080으로 접속해 "Hello World" 메시지가 출력되는지 확인한다.

# 각 요구사항별 학습 내용 정리
* 구현 단계에서는 각 요구사항을 구현하는데 집중한다.
* 구현을 완료한 후 구현 과정에서 새롭게 알게된 내용, 궁금한 내용을 기록한다.
* 각 요구사항을 구현하는 것이 중요한 것이 아니라 구현 과정을 통해 학습한 내용을 인식하는 것이 배움에 중요하다.

### 요구사항 1 - http://localhost:8080/index.html로 접속시 응답
* 고민해야 할 사항
  * buffreredreader로 readline 이아닌 read() 했을떄는 무한 루푸에 빠졌다. -1를 만나지 못한거 같다. 왜? 공백일떄는 못나온것 같다
  * readline() null인 상태로 들어올수도 있다. 아무것도 없는데 읽을려는 상태?

* 배운사항
  * html 파일도 결국에는 서버에서 바이트 단위로 읽어서 뿌려주는것일 뿐이었다.
  * log 사용법 log.degbug(war)등등 ("문자 {}",변수) 저괄호안헤 그다음 내용이 들어가서 출력됨
  * 요청이 두번에 걸쳐서 브라우저에서 서버로 전송된다.
  * 항상 브라우저는 통신 프로토콜? 맞춰 첫줄에 이런형식으로 요청된다.(뒤에한번더 있음) 그리고 요청마다 다른 쓰레드로요청도된다.
    > GET /index.html HTTP/1.1
    >
    > Host: localhost:8070
    >
    > Connection: keep-alive
  * index.html읽어서 보내주면 자동으로 브라우저가 html내부에 적혀있는 link를 다시 서버로 요청한다.  


### 요구사항 2 - get 방식으로 회원가입
* 배운사항
  * 기존에 인데스 에서 다른 html 로 이동할수있었던 이유가 누를떄마다 그 인데스 경로로 파일을 읽어 보내줘서 가능했음
  * response안하면 브라우저에서 에러가 뜬다. 작업이 성공햇던 안했든 response는 꼭 해줘야한다.
* 진행사항
  * 일단 유저 정보 구현 완료 하지만 thread null point exception이 나중에 뜸 왜그런지 확인 필요
  * 요청오는 url 마다 작업이 많을시 따로 구현해야 하면 if 문 남발이 예상됨 해결 방안 이부분 어떻게 바뀌는지 답과 확인 필요하다 -> 확인결과 일단은 이프문으로 진행


### 요구사항 3 - post 방식으로 회원가입
* 배운사항
  * url에를 readline을 null이 아닐때가지 하면 계속입력을 받으려고 기다리고 있다 왜냐하면 buffreredreader가 endoflien 이나 입력 스트림이 닫힐떄가지 입력을 받기 떄문이다.
    그래서 이로 인핸 무한루프 또는 \n 이 계속 받아 질수도 있다.그래서 content-length가 있는 거고 이길만큼만 읽는것이다 그래서 통신규약이 존재하는것이다.
  * post로 요청하기위해서는 html 폼 메서드만 교체해주면 알아서 폼테그안에 있는 값들을 name에 맞춰 폼으로 만들어서 전송한다.
  * GET 데이터 조회 작업시
  * POST 데이터의 상태변경 (추가,수정,삭제)
  * AJAX를 사용하면 PUT DELETE도 사용
* 진행및 추가 확인사항
  * 빈공백이 나온후 폼데이터가 있는줄 알았지만 컨텐트 길이 만큼 받아서 구현해주어야함 거기부터
  * 리팩토링 점검 필요

### 요구사항 4 - redirect 방식으로 이동
* 배운사항
  * 리다이렉트 방법
    * 응답헤더에 Location: 주소 이런식으로 진행하야함
      * 대표 상태코드    
        * 2xx - 성공 클라이언트가 요청한 동작을 수신하여 이해했고 승낙했으면 정상적으로 처리
        * 3xx - 리다이렉션. 클라이언트는 요청을 마치기 위해 추가동작이 필요함
          * 302 - 요청한 리소스가 임시적으로 새로운 URL로 이동햇다고 판단될시
          * 301 - 컨텐트가 새로운 URL로 영원히 이동했다고 판단될시 사용
        * 4xx - 요청오류 클라이언트에 오류가 있음.
        * 5xx - 서버오류. 서버ㅓ가 유효한 요청을 명백하게 수행하지 못함  

### 요구사항 5 - cookie
* 배운사항
  * set-cookie를 하면 그다음 요청은 cookie를 붙혀서 응답한다.
  * 이것으로 서버와 클라이언트가 상태정보를 확인한다.
* 진행사항
  * Set-Cookie로 응답한후 경로에 따라 cookie를 헤더에 보내준다. 그래서 모든 경로에 해당될수있게 Set-Cookie: logined=true; Path=/ path를 지정해주었다.
  * 헤더를 문자열로 만들어주는 부분 리팩토링 완료 하지만 url따라 분기처리하는 부분또한 리팩토링해야 할것같다.


### 요구사항 6 - stylesheet 적용
* 배운사항
  * css 파일은 서버에서 응답시 Content-Type으로 text/css를 보내야한다.


### heroku 서버에 배포 후
*

### 리팩토링 작업
*  ~~url 당 리퀘스트를 핸들하는 부분 분리중~~
* responsheader를 만들고 마지막에 공백줄(\r\n)붙혀주는 부분 숨기기
* createHeader, createBody, checklogin

### 배운내용
* outputstream close 시 자동 flush 됨


### 향후 학습자료
* 네트워크 143p
* http 142p
* 리팩토링 : 리팩토링:코드 품질을 개선하는 객체지향 사고법, 마틴 파울러
