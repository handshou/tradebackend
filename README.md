# Trade

## Author
Hansel Chia.  
Student number: E0191669  

## Extra features 
1. Designed for mobile
1. Field validation
1. Fetching, loading spinner
1. Universal search bar
1. Progressive web app

## Project Structure
Frontend is made with ReactJS. MaterialUI & Antd elements.  
Backend is running on JavaEE Web API, listed below.

## Current Features
These are a list of supported features.

### Admin
| Feature | Description | Progress |
| :------ | ----------- | :------: |
| *Login* | You can hardcode the username and password to be “admin” and “admin” but the server should maintain session information to ensure that the user is logged in before they are allowed to perform admin actions. |
| *Logout* | - |
| *View all users* | Sellers & buyers |
| *Activate user* | - |
| *Deactivate user* | Deactivated user should not be able to log into the system. |

### Seller
| Feature | Description | Progress |
| :------ | ----------- | :------: |
| *Register* | You should decide on the set of attributes that is suitable for sellers/buyers. |
| *Login* | Unlike admin user, you should not hardcode the login details for the sellers and buyers. |
| *Logout* | - |
| *Add item* | Item should have common fields such as: name, description, quantity, category, price, etc. |
| *List/search seller items* | The system should allow sellers to see their list of items. It should also allow them to search/filter by keywords, etc. |
| *Delete item* | Seller should not be able to delete an item if it is already associated with an order. To logical “delete” an item, seller can set the quantity to 0. |
| *Edit item* | - |
| *View all orders* | - |
| *Update order status* | Each order should have the typical kind of status such as: payment confirmed, cancelled, shipped, delivered |
| *Edit profile* | - |

### Buyer
| Feature | Description | Progress |
| :------ | ----------- | :------: |
| *Search items* | Users do not need to be logged in in order to search for items, but they have to be logged in before they can add something to the cart. System should allow users to search by keywords, category, availability and should display item details (e.g. stock level, etc). |
| *Register* | - |
| *Login* | - |
| *Logout* | - |
| *Add items to cart* | - |
| *Check out cart* | While you should take in credit card details, you do not need to integrate with payment gateway and can assume that the payment will always be successful. |
| *View all orders* | Show the list of all the orders of the users. |
| *Add feedback* | After an order has been fulfilled, buyers should be able to leave a feedback (review + rating). They should only be allowed to do this once for each fulfilled order. |
| *Edit profile* | - |

## Web Api

### User
| Type  | REST | URL | User | Desc | Implemented |
| :---: | :--- | :-- | :--: | :--- | :---------: |
| User  | GET  |  /webresources/user/{userId}                   | Y  | Search(uId)      | Y |
| User  | POST |  /webresources/user/                           | -  | Create user      | Y |
| User  | PATCH|  /webresources/user/                           | -  | Edit(userId)     | Y |
| User  | GET  |  /webresources/user/{userId}/store             | Y  | User store       | Y |
| User  | GET  |  /webresources/user/store                      | Y  | My store         | Y |
| User  | GET  |  /webresources/user/{userId}/profile           | Y  | User profile     | Y |
| User  | GET  |  /webresources/user/profile                    | Y  | My profile       | Y |
| User  | GET  |  /webresources/user/login                      | -  | Login            | Y |
| Store | GET  |  /webresources/stores                          | Y  | All stores       | Y |
| Store | GET  |  /webresources/stores/{storeId}                | Y  | Search(storeId)  | Y |
| Item  | POST |  /webresources/user/store/item                 | Y  | Create item      | Y |
| Item  | PATCH|  /webresources/user/store/item                 | Y  | Edit(itemId)     | Y |

### Admin Access
| Type  | REST | URL | Admn | Desc | Implemented |
| :---: | :--- | :-- | :--: | :--- | :---------: |
| Users | GET  |  /webresources/users/{username}                | Y  | Search(keyword)  | Y |
| Users | GET  |  /webresources/users                           | Y  | All users        | Y |
