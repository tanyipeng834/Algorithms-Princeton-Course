<div align="center">
  <h1>GIC-banking-application</h1>
  <img src="./images/gic.png" alt="GIC Logo" />
</div>







## Configurations for Setting up the Project

1. Ensure that Maven is installed on a <b>Windows<b/> computer or you can install it [here](https://maven.apache.org/download.cgi)

2. Run `mvn --version` to ensure that mvn is installed proprerly

3. Download the packages defined in pom.xml with the command `mvn clean install`

4. Run the application by running this command `mvn exec:java` 

5. Run the tests with `mvn tests`

## Assumption for this project 

1. User will be directed to the menu after  he views the Print Statement which is similar to the other 3 options.

## Design For this Applicationa

1. We used Maven as a dependency manager to be able to allow our users to install dependencies without the .jar executable

2. We also implemented the TDD(Test Driven Development) where we wrote tests on Junit first before building the application and then continued to refactor the code and tests subsequently.

3. We also ensure clear documenation by using javadoc to write our clean and readable documentation which can be found in the index.html in the docs folder.
![index.html](./images/index.jpg)
![documentation](./images/documentation.jpg)

4. We used Java due to its performance and it also allows for greater type safety as it is statically typed and it enforces the Object-Orientated Paradigm which allows
us to break down this complex software into many small and modular components.




















