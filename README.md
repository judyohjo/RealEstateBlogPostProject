# RealEstateBlogPostProject (부동산 블로그 프로젝트)

**부동산뉴스 및 새로운 부동산 소식에 대해서 토론할 수 있는 포털입니다.**

**부동산 뉴스 (정보), 질문응답 할 수 있는 게시판 및 중개수수료 계산기를 제공합니다.**

*Note: Still in the process of adding/modifying the pages*


## Purpose

- To provide more real estate current news and enable discussions that are easily accessible to Korean citizens.
- 더 많은 사람들이 최근 부동산 뉴스를 더 쉽게 접하고 그 소식들을 토론할 수 있도록 하기 위해서 개발했습니다.


## Value & Mission

더 많은 사람들이 빠르게 바뀌는 부동산 뉴스 및 정보에 대해 더 쉽게 접할 수 있도록 노력하겠습니다.

## Features/Functionalities

- 메뉴바 (= Navigation bar) (authorisation implemented)
    - 부동산 소개 (all users)
    - 부동산 소식 (registered users)
    - 자유게시판 (registered users)
    - 중개수수료 계산기 (all users)
    - 로그인 (unregistered users)
    - 회원가입 (unregistered users)
    - 로그아웃 (registered users)
    - 글쓰기 (registered users)
- 계정 (= Account)
    - Update Account (아이디 제외 변경 가능)
    - View Account
- 홈페이지 (= Homepage)
    - 메뉴바
    - 부동산이름
    - 로그인/회원가입
    - 검색 기능
    - 인기 순위 (제일 많이 방문한 포스트) 리스트
- 부동산 소개 페이지 (= About us)
    - 부동산 소개
    - 위치
    - 연락처
- 부동산 소식 페이지 (= Real estate news)
    - Add post
        - **Input:** Title, Category, Content, Photo
    - View all posts
        - Edit (수정)
            - 카테고리는 수정을 할 수 없음
        - Delete (삭제)
            - 확인 메세지
- 자유게시판 페이지 (= Discussion)
    - List table
    - Add discussion post
        - All registered users
    - View discussion post
        - All registered users
    - Delete discussion post
- 중개수수료 계산기 (= Calculator)

## 프로그래밍 언어 및 소프트웨어

|  | Language | Software/Tool Used |
| --- | --- | --- |
| **Database** | SQL | MySQL |
| **Backend** | Java Spring framework and Maven | Eclipse 2022 |
| **Frontend** | JSP and HTML/CSS (used Thymeleaf) | Eclipse 2022 |
| **ERD design** | -  | draw.io |
| **Wireframe design** | - | Balsamiq.cloud |
| **Framework** | -  | Swing framework |
| **Check controllers** | Json | Postman |


## Things to work on in the future

- [ ]  Implement CSRF tokens
- [ ]  Photo upload
- [ ]  중개수수료 계산기 페이지
- [ ]  Divide files into folders (code cleaning)
- [ ]  Pagination
- [X]  Navigation bar design
- [ ]  Professional looking design
