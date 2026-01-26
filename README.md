# 💻 코딩 테스트 스터디
## 📅 2026년 1월 4주차 문제

### 📌 월요일  
- [[BOJ] 24337번 가희와 탑](https://www.acmicpc.net/problem/24337)

### 📌 화요일  
- [[PGS] 92344번 파괴되지 않은 건물](https://school.programmers.co.kr/learn/courses/30/lessons/92344)

### 📌 수요일  
- [[BOJ] 9935번 문자열 폭발](https://www.acmicpc.net/problem/9935)

### 📌 목요일  
- [[BOJ] 2250번 트리의 높이와 너비](https://www.acmicpc.net/problem/2250)

### 📌 금요일  
- [[PGS] 60059번 자물쇠와 열쇠](https://school.programmers.co.kr/learn/courses/30/lessons/60059)

### 📌 토요일  
- [[BOJ] 20056번 마법사 상어와 파이어볼](https://www.acmicpc.net/problem/20056)

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

#### ✍️ 풀이 설명 (본인 할당 문제만 작성)

1️⃣ 문제 파악<br>
2️⃣ 접근 과정<br>
3️⃣ 핵심 구현 부분 (**주석 필수**)

#### 💬 리뷰 규칙
- 다른 사람 PR에 최소 1개의 리뷰 남기기
