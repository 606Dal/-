## 스프링 프레임워크 이용한 게시판 (공부)
---
#### 개발 환경
운영체제 : windows10

개발 도구 : STS4

버전

1. JavaSE-11
2. 데이터베이스 : MySQL 8.0.34 (connector-j-8.0.33)
3. WAS : Apache Tomcat 9.0
4. Bootsorap5 (기본 테이블로 보기에는 너무 밋밋해서 사용)
---
만든 기간(중간 저장) : 2024-03-24 ~ 2024-03-28

* 게시판 목록 보기, 글 등록, 수정, 삭제, 답변 까지.
  * 답변 정렬을 위한 bGroup이 bNo(자동 증가)랑 같은 숫자로 게시글 등록할 때 올라가야 하는데, 강의는 오라클 시퀀스를 사용함.
  글 등록할 때 동시에 하기에는 애매해서, 등록 후 바로 이동하게 해서 updatebGroup()을 따로 만듦.

~ 2024-03-31 끝
* jdbcTemplate 사용

---

※ 레거시 프로젝트가 없어서 MVC프로젝트를 따로 만듦.

참고 사이트 (https://crunchify.com/simplest-spring-mvc-hello-world-example-tutorial-spring-model-view-controller-tips/)

---

강의 출처 :  ITGO - Spring (스프링) 활용 Part.1 (담당강사 :	김정일)
