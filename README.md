# digital document vault system

 A secure web-based application designed to help users store, organize, and manage their digital documents efficiently. It provides a simple and intuitive interface for uploading, searching, downloading, and deleting documents while ensuring that users can only access files they are authorized to view. The system also includes dedicated administrative features for managing users, roles, and overall document operations, making it suitable for both personal and organizational use.


## API documentation

### authentication `/api/auth`
| method | endpoint             | access | description                      | request      |
| ------ | -------------------- | ------ | -------------------------------- | ----------------- |
| POST   | `/register` | public | register a new user              | `RegisterRequest` |
| POST   | `/login`    | public | authenticate user and return JWT | `LoginRequest`    |

### employee `/api/documents`
| method | endpoint         | access                     | description           | parameters                                   |
| ------ | ---------------- | -------------------------- | --------------------- | -------------------------------------------- |
| POST   | `/upload`        | `EMPLOYEE`/`MANAGER`/`ADMIN` | upload a document     | multipart form data                          |
| GET    | `/`               | `EMPLOYEE`/`MANAGER`/`ADMIN` | view own documents    | `search`,`category`,`page`,`size`,`sort` |
| GET    | `/{id}` | `EMPLOYEE`/`MANAGER`/`ADMIN` | download own document | `document_id`                                  |
| DELETE | `/{id}`          | `EMPLOYEE`/`MANAGER`/`ADMIN` | delete own document   | `document_id`                                  |

### manager `/api/manager`
| method | endpoint         | access                     | description           | parameters                                   |
| ------ | ---------------- | -------------------------- | --------------------- | -------------------------------------------- |
| GET    | `/documents`               | `MANAGER`/`ADMIN` | view all documents    | `search`,`category`,`page`,`size`,`sort` |
| GET    | `/documents/download/{id}` | `MANAGER`/`ADMIN` | download any document | `document_id`                                  |


### admin `/api/admin`
| method | endpoint         | access                     | description           | parameters                                   |
| ------ | ---------------- | -------------------------- | --------------------- | -------------------------------------------- |
| GET    | `/users`           | `ADMIN`  | list all users   | —                   |
| PATCH  | `/users/{id}/role` | `ADMIN`  | change user role | `UpdateRoleRequest` |
| GET    | `/documents`               | `ADMIN`  | view all documents    | `search`,`category`,`page`,`size`,`sort` |
| GET    | `/documents/download/{id}` | `ADMIN`  | download any document | `document_id`                                  |
| DELETE | `/documents/{id}`          | `ADMIN`  | delete any document   | `document_id`                                  |
| GET    | `/dashboard` | `ADMIN`  | retrieve dashboard statistics |
