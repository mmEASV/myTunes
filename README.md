
<!-- PROJECT SHIELDS -->
<!--
*** I'm using markdown "reference style" links for readability.
*** Reference links are enclosed in brackets [ ] instead of parentheses ( ).
*** See the bottom of this document for the declaration of the reference variables
*** for contributors-url, forks-url, etc. This is an optional, concise syntax you may use.
*** https://www.markdownguide.org/basic-syntax/#reference-style-links
-->
# MyTunes JavaFX
:school_satchel: Exam | 1st Semester | SDE & SCO  

[![Contributors][contributors-shield]][contributors-url]


<!-- PROJECT LOGO -->
<br />
<p align="center">
  <a href="https://user-images.githubusercontent.com/72190589/207565741-1867a0a5-7bd6-46c8-a985-6e0ac64e4cac.png">
    <img src="https://user-images.githubusercontent.com/72190589/207565741-1867a0a5-7bd6-46c8-a985-6e0ac64e4cac.png" alt="Logo" width="350">
  </a>
  <p align="center">
    myTunes desktop application
    <br />
    <a href="https://github.com/mmEASV/myTunes"><strong>Explore the docs »</strong></a>
    <br />
  </p>

<!-- TABLE OF CONTENTS -->
<details open="open">
  <summary><h2 style="display: inline-block">Table of Contents</h2></summary>
  <ol>
    <li>
      <a href="#">About The Project</a>
      <ul>
        <li><a href="#tech-stack">Tech stack</a></li>
        <li><a href="#style">Style</a></li>
      </ul>
    </li>
    <li><a href="#features">Features</a></li>
    <li><a href="#details">Details</a></li>
    <li><a href="#licence">License</a></li>
    <li><a href="#contact">Contact</a></li>
  </ol>
</details>


## Tech stack
* [Java](https://www.java.com/en/)
* [Liberica 19](https://bell-sw.com/libericajdk/)
* [JavaFX](https://www.java.com/en/)


## Style
* [CSS](https://developer.mozilla.org/en-US/docs/Web/CSS/Reference)


<!-- ABOUT THE PROJECT -->
## OOP & CRUD

MyTune project was about learning object-oriented-programming and functionalities.
Basics of programming with a strongly typed language Java and with the help of JavaFx we were able to develop application where we worked on the logic, added extra design pattern and exploring options within JavaFX and Liberica 19
All the requirements are fulfilled, and we also added some extra features we found useful and good for learning purposes.
Our main focus was on lowest coupling as possible and on high cohesion throughout the development.

## Application design:
Our application was re-design by us in Figma 

![Screenshot 2022-12-14 at 10 23 40](https://user-images.githubusercontent.com/72190589/207556989-5708457c-1a69-4c19-8a27-a0754b53f382.png)


## Features
- [x] JDBC database connection
    - [x] Abstract Factory Pattern for DAO
    - [x] Abstract Factory Pattern for different type of connection 
    - [x] Prepared statements securing SQL injection
    - [x] Environmental variables 
    - [x] Dynamic update in the interface
- [x] Songs
    - [x] CRUD
    - [x] Ability to play song 
    - [x] Adding songs into playlist 
    - [x] Removing song from playlist 
    - [x] Dynamic update in the interface
- [x] Song on Playlist
    - [x] Playlist that consist all songs
    - [x] One-to-many relation database 
    - [x] Sorting 
    - [x] Removing of the songs from playlist 
    - [x] Shuffle
- [x] Media player 
    - [x] Singleton pattern 
    - [x] Standard play functionalities / play,pause,next,previous
    - [x] Volume
    - [x] Song duration operation
- [x] Playlist
    - [x] Sign in as Admin
    - [x] Edit products in db
    - [x] Add products to db
    - [x] View ordered products
    - [x] Choice of deleting products / orders
    - [x] QUILL Editor with fb storage upload


  
## Database design

MSSQL Database diagram
![Screenshot 2021-11-15 at 19 39 17](https://user-images.githubusercontent.com/72190589/207555267-3bf24d97-e49f-4f47-bb65-1bab7eb257ea.png)

## UML diagram

![UML](https://user-images.githubusercontent.com/72190589/207556029-147be047-55bd-4140-9b0b-8ac60b41ad57.png)


## Abstraction in myTunes 

### Connection switch statement 

```java
public class ConnectionFactory {
  public static AbstractConnectionFactory getFactory(DatabaseType type) {
    switch (type) {
      case MSSQL:
        return new MSSQLConnection();
      default:
        throw new IllegalArgumentException("Invalid database type: " + type);
    }
  }
}
```

Using abstraction and factories in java helped the application with decoupling in certain degree

### DAO
``` java
public class DAOFactory extends AbstractDAOFactory{
    @Override
    public ISongDAO getSongDAO() throws Exception {
        return new SongDAO();
    }
```

## Environmental variables 

``` java 
 public MSSQLConnection() throws IOException {
        Properties properties = loadConfigFile();
        this.dataSource = new SQLServerDataSource();
        this.dataSource.setDatabaseName(properties.getProperty("db.name"));
        this.dataSource.setUser(properties.getProperty("db.username"));
        this.dataSource.setPassword(properties.getProperty("db.password"));
        this.dataSource.setServerName(properties.getProperty("db.server"));
        this.dataSource.setPortNumber(Integer.parseInt(properties.getProperty("db.port")));
    }
```

Way of how we approached the problem of having sensitive data publicly 

## Application interface 

![Screenshot 2022-12-14 at 10 41 16](https://user-images.githubusercontent.com/72190589/207567000-e4513d5f-8ae8-4210-abba-df79ff2581b9.png)

## Licence

Distributed under the MIT License. See LICENSE for more information.

Team: isEmpty() {true} <br>
2022 SDE & SCO cs project posted here on GitHub. <br>
Hope you will like it! <br>
Thank you for your attention!
TTT :black_nib:
## Contact

Tomas Simko - [@twitter](https://twitter.com/TomasSimko_) - simko.t@email.cz - [@linkedIn](https://www.linkedin.com/in/tomas-simko/)
Balint Farkas - [@twitter](https://twitter.com/TomasSimko_) - simko.t@email.cz - [@linkedIn](https://www.linkedin.com/in/tomas-simko/)
Matej Mazur - [@twitter](https://twitter.com/TomasSimko_) - simko.t@email.cz - [@linkedIn](https://www.linkedin.com/in/tomas-simko/)
Julian Hesberg - [@twitter](https://twitter.com/TomasSimko_) - simko.t@email.cz - [@linkedIn](https://www.linkedin.com/in/tomas-simko/)

Project Link: [https://github.com/mmEASV/myTunes]()



<!-- MARKDOWN LINKS & IMAGES -->
<!-- https://www.markdownguide.org/basic-syntax/#reference-style-links -->
[contributors-shield]: https://img.shields.io/github/contributors/mmEASV/myTunes.svg?style=for-the-badge
[contributors-url]: https://github.com/mmEASV/myTunes/graphs/contributors

