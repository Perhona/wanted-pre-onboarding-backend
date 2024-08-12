# wanted-pre-onboarding-backend
프리온보딩 백엔드 인턴십 선발과제

# HiredHub API

HiredHub는 기업과 구직자를 연결하는 채용 플랫폼입니다. 이 웹 서비스는 기업이 구직자에게 쉽게 접근할 수 있도록 돕고, 구직자가 자신의 이력서를 기업에 제출할 수 있는 기능을 제공합니다.


## 개발 환경
- 언어: Java 17
- 프레임워크: Spring Boot 3.2.9
- 빌드 도구: Maven
- 데이터베이스: MySQL
- ORM: Spring Data JPA

## 기능

### 회사

- 채용 공고 등록/수정/삭제


### 사용자
- 채용 공고 목록 조회
- 채용 공고 목록 검색
- 채용 상세 조회
- 채용 공고 지원

## API 명세

### 1. 채용공고 등록 `POST /jobPostings`
- 새로운 채용 공고를 등록합니다.

```json
{
  "position": "백엔드 주니어 개발자",
  "country": "한국",
  "region": "서울",
  "reward": 1000000,
  "techStack": "Python",
  "jobDescription": "원티드랩에서 백엔드 주니어 개발자를 채용합니다. 자격요건은..",
  "companyId": 1
}
```
### 2. 채용공고 수정 `PUT /jobPostings/{id}`
- 기존 채용 공고를 수정합니다.
```json
{
  "position": "백엔드 주니어 개발자",
  "reward": 1500000,
  "techStack": "Django",
  "jobDescription": "원티드랩에서 백엔드 주니어 개발자를 '적극' 채용합니다. 자격요건은.."
}
```
### 3. 채용공고 삭제 `DELETE /jobPostings/{id}`
- 특정 채용 공고를 삭제합니다.
### 4. 채용공고 목록 조회 `GET /jobPostings`
- 모든 채용 공고 목록을 조회합니다.
### 5. 채용공고 목록 검색 `GET /jobPostings/search`
특정 조건(회사명, 포지션)에 맞는 채용 공고를 검색합니다. 
```html
예시 URL: /jobPostings/search?companyName=원티드&position=spring
예시 URL: /jobPostings/search?companyName=원티드
예시 URL: /jobPostings/search?position=spring
```
### 6. 채용 상세 조회 `GET /jobPostings/{id}`
- 특정 채용 공고의 상세 정보를 조회합니다.
### 7. 채용공고 지원 `POST /applications`
- 사용자가 특정 채용 공고에 지원합니다. 지원자는 해당 공고에 1회만 지원할 수 있습니다.
```json
{
  "userId": 123,
  "jobPostingId": 1
}
```

