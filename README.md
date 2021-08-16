## About
This REST server provides API for a user to list the available shows to a movie and book his selected seats
Implementation was done using Spring Boot. 
Database supporting the backend is MySQL

## Setup

### Set up database
Database with the loaded data can be set up using `Docker`
Run the following command from the project root directory

`docker run -d --rm -p 3307:3306 -e MYSQL_ROOT_PASSWORD=root -e MYSQL_DATABASE=gen2 -v $PWD/data:/var/lib/mysql --name mysql mysql:5.7.22`

### Accessing the API
Run the project and the API can be accessed at `http://127.0.0.1:8080`

## API

### Login
- API: `/login`
- Type: `POST`
- Params: `user=<username>` and `passwd=<password>`
- This endpoint will generate a token valid for 12 hours for the user. For the sake of simplicity, password validation is not done

### List the available shows
- API: `/shows`
- Type: `GET`
- Params: NA
- This endpoint will list the available shows and the details of the cinema and movies that's showing

### List the available seats
- API: `/seats-for-shows`
- Type; `GET`
- Params: Query Parameter with the show Id for which we are checking availability
  - `?showId=1`
- This endpoint will list the available seat numbers for the selected show

### Block the selected seats
- API: `/select-seats`
- Type: `POST`
- Params:
    - Body: List of String numbers of selected seats. ex. `["1A", "1B", "1C"]`
    - Query Parameter: Id of the show we are selecting this for. `?showId=1`
- This endpoint will block the selected seats by the user and return an error if he tries to overbook
- The blocked seats will be released after 2 minutes

### Book the seats that we blocked
- API: `/book`
- Type: `POST`
- Params:
  - Query Parameter: Blocking ID returned from the previous step `?blockingId=x`
- This endpoint will finalise the booking after payment is done. Payment is simulated with a function that gives `true` / `false` with a 50/50 probability. If payment is successful, details of the booking will be returned

