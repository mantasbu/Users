# Users App

### Implementation
For implementation used https://gorest.co.in/ public API

#### 1 Displaying list of users
- After app is open list of users is displayed (only users from last page of the endpoint)
- Each entry contains name, email address, status and gender
- Loading and error state

#### 2 Adding new user
- After + button is clicked pop up dialog is displayed with name and email entries
- After confirmation and successful user creation (201 response code) item is added to the list

#### 3 Removing existing user
- After item long press pop up dialog is displayed with question “Are you sure you want to remove this user?“
- After OK is clicked and user is removed (204 response code) item is deleted from the list

### Technical requirements
- Application must be developed in Kotlin with minimum Android SDK version of 21
- You are free to use whatever frameworks or tools you see fit
- Application needs to support device rotation
- Design should follow Material design guidelines
- RxJava or Coroutines
- Architecture one of MVP/MVVM/MVI
- Dependency injection with Dagger 2 or Hilt
- Unit tests