## Reading headers

> https://stackoverflow.com/questions/26212844/how-to-read-authorization-header-in-jax-rs-service

## User Access

| Type  | REST | URL | User | Desc | Implemented |
| :---: | :--- | :-- | :--: | :--- | :---------: |
| User  | GET  |  /webresources/user/{userId}                   | Y  | Search(uId)      | Y |
| User  | POST |  /webresources/user/                           | -  | Create user      | Y |
| User  | PATCH|  /webresources/user/                           | -  | Edit(userId)     | Y |
| User  | GET  |  /webresources/user/{userId}/store             | Y  | User store       | Y |
| User  | GET  |  /webresources/user/store                      | Y  | My store         | Y |
| User  | GET  |  /webresources/user/{userId}/profile           | Y  | User profile     | Y |
| User  | GET  |  /webresources/user/profile                    | Y  | My profile       | Y |
| Store | GET  |  /webresources/stores                          | Y  | All stores       | Y |
| Store | GET  |  /webresources/stores/{storeId}                | Y  | Search(storeId)  | Y |
| Item  | POST |  /webresources/item	                          | Y  | Create item      | Y |
| Item  | PATCH|  /webresources/item/                           | Y  | Edit(itemId)     | Y |

## Admin Access

| Type  | REST | URL | Admn | Desc | Implemented |
| :---: | :--- | :-- | :--: | :--- | :---------: |
| Users | GET  |  /webresources/users/{username}                | Y  | Search(keyword)  | Y |
| Users | GET  |  /webresources/users                           | Y  | All users        | Y |
