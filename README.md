# Spring Boot 기반 JWT 인증/인가 및 AWS 배포

스파르타 코딩클럽 바로인턴 11기 Spring 백엔드 개발 직무 과제입니다. (2025.04.18 ~ 2025.04.20)

[배포된 API 문서 확인하기](http://3.34.42.209:8080/swagger-ui/index.html)

<br/>

## 프로젝트 설명

1️⃣ **Spring Boot**를 이용하여 JWT 인증/인가 로직과 API를 구현한다.

2️⃣ **Junit** 기반의 테스트 코드를 작성한다.

3️⃣ **Swagger** 로 API를 문서화 한다.

4️⃣ 애플리케이션을 **AWS EC2**에 배포하고, 실제 환경에서 실행되도록 구성한다.

<br/>

## 실행 방법

EC2에서 실행 중인 API의 엔드포인트는 `http://3.34.42.209:8080` 이며, 아래의 주소를 통해 API 문서를 확인할 수 있습니다.

<div align="center"><img width="500" src="https://github.com/user-attachments/assets/11de46d5-d725-4a19-834c-7e4614062090" /><br/><a href="http://3.34.42.209:8080/swagger-ui/index.html">http://3.34.42.209:8080/swagger-ui/index.html</a></div>

과제 테스트를 위한 시나리오는 다음과 같습니다.

1. `/signup`을 통해 일반 사용자로 회원가입을 합니다.
    ```
    {
      "username": "user",
      "nickname": "user",
      "password": "123456"
    }
    ```
2. `/admin-signup`을 통해 관리자 권한으로 회원가입을 합니다.
    ```
    {
      "username": "admin",
      "nickname": "admin",
      "password": "123456"
    }
    ```
3. `/login`을 통해 관리자 계정에 로그인하고, 이때 반환되는 토큰 값에서 `Bearer `을 제외한 값을 복사합니다.
    ```
    {
      "username": "admin",
      "password": "123456"
    }
    ```
    <img width="1000" src="https://github.com/user-attachments/assets/c8f9fe32-523d-4559-b586-33c01f4cae73" />
4. 오른쪽 상단의 Authorize 버튼을 눌러 복사한 토큰 값을 입력합니다.
   <br/><img width="500" src="https://github.com/user-attachments/assets/766182d2-a02c-44f3-9fcc-a1efddadbca5" />
5. `/admin/users/{userId}/roles`에서 자물쇠가 잠긴 것을 확인했다면, userId에 1번 단계에서 가입했던 유저의 id를 입력하고 관리자 권한을
   부여합니다.
   <br/><img width="500" src="https://github.com/user-attachments/assets/6d6f7f2b-b7eb-44c7-bc6d-36979ca2ded5" />

<br/>

## API 명세

<table>
<thead>
<tr>
<th>METHOD</th>
<th>URI</th>
<th>DESCRIPTION</th>
<th>REQUEST</th>
<th>RESPONSE</th>
</tr>
</thead>
<tbody>
<tr>
<td><code>POST</code></td>
<td>/signup</td>
<td>일반 회원 가입</td>
<td>
<pre>Content-Type: application/json<br/><br/>{
  "username": "user",
  "nickname": "user",
  "password": "password"
}</pre>
</td>
<td>
<pre>201 Created<br/><br/>{
  "username": "user",
  "nickname": "user",
  "roles": [
    {
      "role": "USER"
    }
  ]
}
</pre>
</td>
</tr>
<tr>
<td><code>POST</code></td>
<td>/admin-signup</td>
<td>관리자 회원 가입</td>
<td>
<pre>Content-Type: application/json<br/><br/>{
  "username": "admin",
  "nickname": "admin",
  "password": "password"
}</pre>
</td>
<td>
<pre>201 Created<br/><br/>{
  "username": "admin",
  "nickname": "admin",
  "roles": [
    {
      "role": "ADMIN"
    }
  ]
}
</pre>
</td>
</tr>
<tr>
<td><code>POST</code></td>
<td>/login</td>
<td>로그인</td>
<td>
<pre>Content-Type: application/json<br/><br/>{
  "username": "user",
  "password": "password"
}</pre>
</td>
<td>
<pre>200 OK<br/><br/>{
  "token": "Bearer eyJhb...",
}
</pre>
</td>
</tr>
<tr>
<td><code>PATCH</code></td>
<td>/admin/users/{userId}/roles</td>
<td>관리자 권한 부여</td>
<td>
</td>
<td>
<pre>200 OK<br/><br/>{
  "username": "user",
  "nickname": "user",
  "roles": [
    {
      "role": "ADMIN"
    }
  ]
}
</pre>
</td>
</tr>
</tbody>
</table>
