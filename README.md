# TinyTasks

(very) basic task management app.

## Development

The application consists of a frontend and a backend. Both can be started separately. The frontend is
[Angular](https://angular.io/) based and the backend is based on [Spring Boot](https://spring.io/projects/spring-boot).

Before you can start developing you need to set up your development environment. You can find good and clear
instructions on the Angular website in the [Quickstart](https://angular.io/guide/quickstart) guide. The
backend requires a [PostgreSQL](https://www.postgresql.org/) database server that is provided by a
[Docker](https://www.docker.com/) container. Thus, Docker is required. You can find the information you need to
set up the runtime environment on the Docker website under [Get Started](https://www.docker.com/get-started).

### Frontend

The fronted was generated with [Angular CLI](https://github.com/angular/angular-cli). Run `yarn` to install the
dependencies for the app. You can also add new dependencies via `yarn add`. Run `yarn start` for a dev server.
Navigate to `http://localhost:4200/`. The app will automatically reload if you change any of the source files.

To run the dev server without starting the backend use `yarn start-local`. The application will then store its
data in the browser's local storage instead of sending the data to the backend.

Run `yarn lint` to lint your application and `yarn test` to execute the unit tests via [Karma](https://karma-runner.github.io).

### Backend

The backend was generated with [Spring Initializr](https://start.spring.io/). Run `docker-compose up -d` to launch
the PostgreSQL docker container. Run `./gradlew bootRun` for a dev server. The server is available under `http://localhost:8080/`.

Run `docker exec -it tiny-tasks_postgres_1 psql -U tiny_task` to get the PostgreSQL console.
Then run `\d` to see a list of relations, `\d+ task` to see the table task. When executing statements, it is necessary to end 
them with a semicolon i.e. `select * from users;` or they will not execute since Postgres does not know that the statement ended.
`\q` or control+d to quit the console.

Run `./gradlew test` to execute the tests.


### Commits

This project uses [gitmoji](https://gitmoji.carloscuesta.me/) for commit-messages.
Commits that did not use gitmoji but were visible on the repository frontpage were renamed
via `git rebase -i --root.

Whenever you add a new feature that's worth co`mmiting, commit. 
You added a working method? Commit. You fixed a typo? Commit. 
You fixed a file's wrong indentation? Commit. 
There's nothing wrong commiting small changes, 
as soon as the commit is relevant.

Making small commits allows you to use very powerful tools like git-bisect.

### CI

[GitHub Actions (beta)](https://github.com/features/actions)

### Passwords, API-Keys

[git-secret](https://git-secret.io/)

### [To-Do](https://github.com/mindsmash/tiny-tasks/issues)
