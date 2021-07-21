# AfOfferTest

Projet developped with Spring Boot.
Project name: offertest

## Structure du projet 
Le projet structured as following:<br />
    *Source code : located in /offertest/src/main/java/com/example/offertest/  
	- OffertestApplication.java: application main  
        - aspect folder for service inputs and outputs logging.  
        - service folder with logics to validate inputs before DB access.  
        - repository where UserRepository is define to handle MongoDB requests. 
	- controlller folder with user REST controller.  
	- model folder for user document definition.  
	- exception contains exceptions handling class.  
    * Test folder: /offertest/src/test/java/com/example/offertest/  
	- OffertestApplicationTests.java: Test MVC REST API requests.  
	- UserServiceTests.java: Test service methods with mocked userRepository.  
    * READMD: with project details  
    * PostMan Collection: available in /offertest/OfferTestPostMan.postman_collection.json  

## Build and test project
Project can be tested with maven with line command or via IDE like Eclipse  
	*Postman with Eclipse:  
		- Run project: right click on OffertestApplication.java -> Rus As -> Java Application  
		- Test with Post Man collection  
	*Unit test: with maven test or right click on the project -> Rus As -> Maven test  
  
## Constraints
Only French user can register (User.residenceCountry should be France, not case sentive).  
User have to be an adult (age >= 18)  
