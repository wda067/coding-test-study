# 💻 코딩 테스트 스터디
## 📅 2026년 2월 1주차 문제

### 📌 월요일  
- [[BOJ] 21609번 상어 중학교](https://www.acmicpc.net/problem/21609)

### 📌 화요일  
- [[PGS] 118668번 코딩 테스트 공부](https://school.programmers.co.kr/learn/courses/30/lessons/118668)

### 📌 수요일  
- [[BOJ] 2812번 크게 만들기](https://www.acmicpc.net/problem/2812)

### 📌 목요일  
- [[PGS] 86971번 전력망을 둘로 나누기](https://school.programmers.co.kr/learn/courses/30/lessons/86971)

### 📌 금요일  
- [[PGS] 92342번 양궁대회](https://school.programmers.co.kr/learn/courses/30/lessons/92342)

### 📌 토요일  
- [[PGS] 64065번 튜플](https://school.programmers.co.kr/learn/courses/30/lessons/64065)

## ✅ 스터디 진행 방법
1. 현재 저장소를 자신의 깃허브 계정으로 **포크**한다.
2. 포크한 저장소를 로컬 환경에 **클론**한다.
```
git clone https://github.com/{github_id}/coding-test-study.git
```
3. 자신의 깃허브 ID로 **브랜치를 생성**한다.
```
git checkout -b {github_id}
```
4. 본인의 폴더에 문제 풀이 코드를 작성하고 커밋 메시지와 함께 **커밋**한다.
```
git add .
git commit -m "feat: [BOJ] 1234번"
```
5. 작업이 완료되면, 포크한 개인 저장소의 **해당 브랜치로 푸시**한다.
```
git push origin {github_id}
```
6. 원본 저장소의 `main` 브랜치를 대상으로 **PR**을 생성한다.
7. 해당 주차 동안 문제 풀이를 계속 커밋하며 풀이를 인증한다.
8. 복습이 필요하다고 판단되는 문제는 Issue로 등록하고, 추후에 재풀이 후 `review` 타입으로 커밋한다.

## ✅ 패키지 구조
- **형식**: `GitHub ID/플랫폼/플랫폼_문제 번호`
  - **예시**:
    - `wda067/boj/BOJ_1234.java`
    - `wda067/boj/BOJ_1234_2.java`
   
> `_2`, `_3` ... 는 풀이 횟수를 의미한다.

## ✅ 커밋 컨벤션
- **형식**: `타입: 플랫폼_문제 번호`
  - **예시**:
    - `feat: BOJ_1234`
    - `review: BOJ_1234_2`
- **플랫폼 코드**
  - **BOJ**: 백준
  - **PGS**: 프로그래머스
- **타입**
  - **feat**: 새로운 문제 풀이 추가
  - **review**: 기존 문제 재풀이
  - **fix**: 기존 문제 풀이의 오류 수정
  - **refactor**: 코드 리팩토링
  - **chore**: 기타 변경 사항

## ✅ PR 컨벤션

### 🔖 PR 제목

- **제목**: `년-월 N주차 문제 풀이`
- **예시**: `2026-01 2주차 문제 풀이`

### 📝 PR 내용
#### ✅ 제출 결과

- 요일마다 제출 성공 화면 캡쳐 (알고리즘, SQL)
- 문제 번호 + 제출 시간이 함께 보이도록 첨부

#### 💬 리뷰 규칙
- 다른 사람 PR에 최소 1개의 리뷰 남기기
