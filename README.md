# SpringBoot-init
这是一个springboot3.x的登录注册模板，后面~~或许~~会加更多功能，使用了postgresql+mybatis-plus+redis+jwt+spring aop，用于进行springboot项目的初始化。

## 模块及功能

* [user](#user)
    1. [login](#1-login)
    1. [register](#2-register)
    1. [logout](#3-logout)
    1. [page user vo](#4-page-user-vo)
    1. [upload avatar](#5-upload-avatar)
    1. [update user avatar](#6-update-user-avatar)
    1. [update username](#7-update-username)
    1. [get login](#8-get-login)
* [admin](#admin)
    1. [get user by id](#1-get-user-by-id)
    1. [get userVO by id](#2-get-uservo-by-id)
    1. [list user page](#3-list-user-page)

--------

## user

### 1. login

用户登录

***请求方法:***

```bash
Method: POST
Type: RAW
URL: http://localhost:9099/user/login
```

***请求体:***

```json        
{
    "account":"shimakaze",
    "password":"yukikaze"
}
```

***返回示例:***

```json
{
    "code": 0,
    "message": "OK",
    "data": {
        "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VySWQiOiIwYTMyMTBmOTYwNDE0OGMxOTUyNmM0MThlMDQzODNjZiIsImV4cCI6MTY4NjA1NDMwM30.NU47e89_h2KiFLSZQ0z-jYc9I2rHA4C0fVDi4Sxqth0"
    }
}
```

### 2. register

用户注册

***请求方法:***

```bash
Method: POST
Type: RAW
URL: http://localhost:9099/user/register
```

***请求体:***

```json        
{
    "account":"shimakaze",
    "userName":"shimakaze",
    "password":"yukikaze",
    "checkPassword":"yukikaze"
}
```

***返回示例:***

```json
{
    "code": 0,
    "message": "OK",
    "data": "f51d15f81d4c4632a6d0948d5e68f03f"
}
```

### 3. logout

用户登出

***请求方法:***

```bash
Method: POST
Type: 
URL: http://localhost:9099/user/logout
```

***请求头:***

| Key | Value | Description |
| --- | ------|-------------|
| Authorization | eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VySWQiOiIwYTMyMTBmOTYwNDE0OGMxOTUyNmM0MThlMDQzODNjZiIsImV4cCI6MTY4NTkwMjcwMX0.pQNFqnCOfyu2oUEjXBgChbHr0QTDFPHa9ROkHlaUKaI | token |

***返回示例:***

```json
{
    "code": 0,
    "message": "OK",
    "data": true
}
```

### 4. page user vo

分页获取用户脱敏信息

***请求方法:***

```bash
Method: POST
Type: RAW
URL: http://localhost:9099/user/page/vo
```

***请求头:***

| Key | Value | Description |
| --- | ------|-------------|
| Authorization | eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VySWQiOiIwYTMyMTBmOTYwNDE0OGMxOTUyNmM0MThlMDQzODNjZiIsImV4cCI6MTY4NTk2ODk5NX0.HLBXoax1gOM_17S7Aujho4bCh2EGSpb0hcMRzg1E3SI | token |

***请求体:***

```json        
{
    "userName":"kaze"
}
```

***返回示例:***

```json
{
    "code": 0,
    "message": "OK",
    "data": {
        "records": [
            {
                "userId": "25128e3c32e04ca3a2dd51224ccea5a0",
                "userName": "yukikaze",
                "account": "yukikaze",
                "avatar": "/img/5b2d2c643c104d6babc26ff64f160011.png",
                "role": "user",
                "createTime": "2023-05-26T03:14:56.384382",
                "updateTime": "2023-05-26T03:19:43.082008",
                "lastLoginTime": "2023-05-26T03:19:09.91185",
                "lastLoginIp": "0:0:0:0:0:0:0:1"
            },
            {
                "userId": "0a3210f9604148c19526c418e04383cf",
                "userName": "shimakaze",
                "account": "shimakaze",
                "avatar": "/img/5b2d2c643c104d6babc26ff64f160011.png",
                "role": "admin",
                "createTime": "2023-05-25T07:12:22.185478",
                "updateTime": "2023-05-26T02:27:05.35702",
                "lastLoginTime": "2023-05-27T02:25:03.488249",
                "lastLoginIp": "0:0:0:0:0:0:0:1"
            }
        ],
        "total": 2,
        "size": 10,
        "current": 1,
        "orders": [],
        "optimizeCountSql": true,
        "searchCount": true,
        "maxLimit": null,
        "countId": null,
        "pages": 1
    }
}
```

### 5. upload avatar

上传用户头像

***请求方法:***

```bash
Method: POST
Type: FORMDATA
URL: http://localhost:9099/upload/avatar
```

***请求头:***

| Key | Value | Description |
| --- | ------|-------------|
| Authorization | eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VySWQiOiIwYTMyMTBmOTYwNDE0OGMxOTUyNmM0MThlMDQzODNjZiIsImV4cCI6MTY4NTkwMjcwMX0.pQNFqnCOfyu2oUEjXBgChbHr0QTDFPHa9ROkHlaUKaI | token |

***请求体:***

| Key | Value | Description |
| --- | ------|-------------|
| file | 5b2d2c643c104d6babc26ff64f160011.png | 图片 |

***返回示例:***

```json
{
    "code": 0,
    "message": "OK",
    "data": {
        "fileName": "/img/56721c673812492abc368a7ce5526b1c.jpeg"
    }
}
```

### 6. update user avatar

更新用户头像

***请求方法:***

```bash
Method: POST
Type: RAW
URL: http://localhost:9099/user/update/avatar
```

***请求头:***

| Key | Value | Description |
| --- | ------|-------------|
| Authorization | eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VySWQiOiIwYTMyMTBmOTYwNDE0OGMxOTUyNmM0MThlMDQzODNjZiIsImV4cCI6MTY4NTkwMjcwMX0.pQNFqnCOfyu2oUEjXBgChbHr0QTDFPHa9ROkHlaUKaI | token |

***请求体:***

```json        
{
    "avatar":"/img/5b2d2c643c104d6babc26ff64f160011.png"
}
```

***返回示例:***

```json
{
    "code": 0,
    "message": "OK",
    "data": true
}
```

### 7. update username

更新用户名

***请求方法:***

```bash
Method: POST
Type: RAW
URL: http://localhost:9099/user/update/username
```

***请求头:***

| Key | Value | Description |
| --- | ------|-------------|
| Authorization | eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VySWQiOiIwYTMyMTBmOTYwNDE0OGMxOTUyNmM0MThlMDQzODNjZiIsImV4cCI6MTY4NTkwMjcwMX0.pQNFqnCOfyu2oUEjXBgChbHr0QTDFPHa9ROkHlaUKaI | token |

***请求体:***

```json        
{
    "userName":"shimakaze"
}
```

***返回示例:***

```json
{
    "code": 0,
    "message": "OK",
    "data": true
}
```

### 8. get login

获取当前登录用户的脱敏信息

***请求方法:***

```bash
Method: GET
Type: 
URL: http://localhost:9099/user/get/login
```

***请求头:***

| Key | Value | Description |
| --- | ------|-------------|
| Authorization | eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VySWQiOiIwYTMyMTBmOTYwNDE0OGMxOTUyNmM0MThlMDQzODNjZiIsImV4cCI6MTY4NTkwMjcwMX0.pQNFqnCOfyu2oUEjXBgChbHr0QTDFPHa9ROkHlaUKaI | token |

***返回示例:***

```json
{
    "code": 0,
    "message": "OK",
    "data": {
        "userId": "0a3210f9604148c19526c418e04383cf",
        "userName": "shimakaze",
        "account": "shimakaze",
        "avatar": "/img/5b2d2c643c104d6babc26ff64f160011.png",
        "role": "admin",
        "createTime": "2023-05-25T07:12:22.185478",
        "updateTime": "2023-05-27T10:31:23.057421",
        "lastLoginTime": "2023-05-27T02:31:14.483716",
        "lastLoginIp": "0:0:0:0:0:0:0:1"
    }
}
```

## admin

### 1. get user by id

根据用户ID获取用户信息

***请求方法:***

```bash
Method: GET
Type: FORMDATA
URL: http://localhost:9099/user/get/id
```

***请求头:***

| Key | Value | Description |
| --- | ------|-------------|
| Authorization | eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VySWQiOiIwYTMyMTBmOTYwNDE0OGMxOTUyNmM0MThlMDQzODNjZiIsImV4cCI6MTY4NTkwMjcwMX0.pQNFqnCOfyu2oUEjXBgChbHr0QTDFPHa9ROkHlaUKaI | token |

***请求体:***

| Key | Value | Description |
| --- | ------|-------------|
| userId | 0a3210f9604148c19526c418e04383cf | 用户ID |

***返回示例:***

```json
{
    "code": 0,
    "message": "OK",
    "data": {
        "userId": "0a3210f9604148c19526c418e04383cf",
        "userName": "shimakaze",
        "account": "shimakaze",
        "password": null,
        "avatar": "/img/5b2d2c643c104d6babc26ff64f160011.png",
        "role": "admin",
        "createTime": "2023-05-25T07:12:22.185478",
        "updateTime": "2023-05-27T02:31:23.057421",
        "lastLoginTime": "2023-05-27T02:31:14.483716",
        "lastLoginIp": "0:0:0:0:0:0:0:1",
        "isDelete": "0"
    }
}
```
### 2. get userVO by id

根据用户ID获取用户脱敏信息

***请求方法:***

```bash
Method: GET
Type: FORMDATA
URL: http://localhost:9099/user/get/vo
```

***请求头:***

| Key | Value | Description |
| --- | ------|-------------|
| Authorization | eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VySWQiOiIwYTMyMTBmOTYwNDE0OGMxOTUyNmM0MThlMDQzODNjZiIsImV4cCI6MTY4NTk2ODk5NX0.HLBXoax1gOM_17S7Aujho4bCh2EGSpb0hcMRzg1E3SI | token |

***请求体:***

| Key | Value | Description |
| --- | ------|-------------|
| userId | 0a3210f9604148c19526c418e04383cf | 用户ID |

***返回示例:***

```json
{
    "code": 0,
    "message": "OK",
    "data": {
        "userId": "0a3210f9604148c19526c418e04383cf",
        "userName": "shimakaze",
        "account": "shimakaze",
        "avatar": "/img/5b2d2c643c104d6babc26ff64f160011.png",
        "role": "admin",
        "createTime": "2023-05-25T07:12:22.185478",
        "updateTime": "2023-05-27T02:31:23.057421",
        "lastLoginTime": "2023-05-27T02:31:14.483716",
        "lastLoginIp": "0:0:0:0:0:0:0:1"
    }
}
```

### 3. list user page

分页获取用户信息

***请求方法:***

```bash
Method: POST
Type: RAW
URL: http://localhost:9099/user/list/page
```

***请求头:***

| Key | Value | Description |
| --- | ------|-------------|
| Authorization | eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VySWQiOiIwYTMyMTBmOTYwNDE0OGMxOTUyNmM0MThlMDQzODNjZiIsImV4cCI6MTY4NTk2ODk5NX0.HLBXoax1gOM_17S7Aujho4bCh2EGSpb0hcMRzg1E3SI | token |

***请求体:***

```json        
{
    "userName":"kaze"
}
```

***返回示例:***

```json
{
    "code": 0,
    "message": "OK",
    "data": {
        "records": [
            {
                "userId": "25128e3c32e04ca3a2dd51224ccea5a0",
                "userName": "yukikaze",
                "account": "yukikaze",
                "password": null,
                "avatar": "/img/5b2d2c643c104d6babc26ff64f160011.png",
                "role": "user",
                "createTime": "2023-05-26T03:14:56.384382",
                "updateTime": "2023-05-26T03:19:43.082008",
                "lastLoginTime": "2023-05-26T03:19:09.91185",
                "lastLoginIp": "0:0:0:0:0:0:0:1",
                "isDelete": "0"
            },
            {
                "userId": "0a3210f9604148c19526c418e04383cf",
                "userName": "shimakaze",
                "account": "shimakaze",
                "password": null,
                "avatar": "/img/5b2d2c643c104d6babc26ff64f160011.png",
                "role": "admin",
                "createTime": "2023-05-25T07:12:22.185478",
                "updateTime": "2023-05-27T02:31:23.057421",
                "lastLoginTime": "2023-05-27T02:31:14.483716",
                "lastLoginIp": "0:0:0:0:0:0:0:1",
                "isDelete": "0"
            }
        ],
        "total": 2,
        "size": 10,
        "current": 1,
        "orders": [],
        "optimizeCountSql": true,
        "searchCount": true,
        "maxLimit": null,
        "countId": null,
        "pages": 1
    }
}
```

---
[Back to top](#springboot-init)
