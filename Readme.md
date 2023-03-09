
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)
[![Java CI with Maven](https://github.com/Sabya1234/AdvanceAutomationWithSelenium/actions/workflows/CI.yml/badge.svg)](https://github.com/Sabya1234/AdvanceAutomationWithSelenium/actions/workflows/CI.yml)


## Don't forget to give a :star2: if you like the project

## :question: What is this Repository about?
First of all this is not any Test automation Framework or not any E2E Tests,This contains Multiple Advance Automation Scenario
covered through latest `Selenium(version 4.8.0)` with help of some `Maven` as build tool and `TestNG` as Test framework we have triggered few test
also through `github actions` you can find in the Actions section or you can click `Java CI With Mavan` badge icon mentioned above.

- This repo has example codes with Selenium 4 features and as well as `PDF files read and validate`,`QR Code validation`,`ZAPWithSelenium` and `Selenium4 CDP and few testswritten with BiDi(implemented throughCDP)`
feature .
- Libraries  used for scenarios are: [zxing for QR Code](https://mvnrepository.com/artifact/com.google.zxing/javase/3.5.0), [PDFBOX for pdf validation](https://mvnrepository.com/artifact/org.apache.pdfbox/pdfbox/2.0.27),
  [zap-clientapi](https://mvnrepository.com/artifact/org.zaproxy/zap-clientapi/1.11.0),[TestNG](https://mvnrepository.com/artifact/org.testng/testng),[Selenium 4](https://mvnrepository.com/artifact/org.seleniumhq.selenium/selenium-java/4.8.0)
- This repo uses `Maven` as build tool and `TestNG` testing framework to run the tests.
- This repo has `.github/workflows/ci.yml` file for executing through CI pipeline in github actions.

## Talking more about the Scenarios Covered in this project:

  ### PDF Scenarios
      -Location of the Tests : `src/test/java/PDFScenario`
   #### Scenario Covered
   1. Validate PDF file Texts via directly visiting pdf link and validate PDF file opened on browser 
   2. Validate PDF file Total No of pages, get Metadata Information (for example: author, creation date, encryption type)
   3. Validate PDF Texts for particular pages starting page number to end page Number 
   4. Validate PDF file that opened on new Window/Tab after clicking on certain linktext
   5. Validate PDF file Texts considering file has present in codebase under `src/test/resource`and validate content 
  ### QR CODE
     -Location of the Tests : `src/test/java/QRCode`
   #### Scenario Covered
   1. Extract and Validate QR CODE texts(order id / website link) from QR code that has Image url non encrypted
   2. Extract and Validate QR CODE texts(order id / website link) from QR code that has Image url base64 encrypted

  ### CDP(Chrome Dev tools)
     -Location of the Tests : `src/test/java/CDP`
   #### Scenario Covered
   1. Perform Basic Auth using CDP
   2. Perform Basic Auth through Selenium Internal Class
   3. `Most widely used` Mock Geolocation and validate the website behaviour for that location 
   4. Extract and check Network logs through CDP
   5. Intercepting Network (Experimental)
   #### TO DO
    -All above scenarios Through Bidi (Bidirectional Protocol), since its still under development
  ### OWASP ZAP DAST Scan with selenium
     -Location of the Tests : `src/test/java/Security`
   #### Scenario Covered
   1. Perform DAST scan on AUT through ZAPClient-API with selenium
   #### TO-DO
   1. More scenarios through ZAP API
   2. integrate ZAP tests through CI pipeline


## How to run the Tests?


  ### 1. TestNG:
    - Right-Click on the `testng.xml` and
      select `Run ...\testng.xml`

  ### 2. Maven:
    - To run the tests in headless mode update in tests chromeoption , will provide a baseTest class which will accept value from command line
     and execute on headless mode

      `mvn clean install` 

    - NOTE: As of now mostly all tests are made as headless to perform faster on CI.

  ### 3. Through On demand Github Actions or through PR or Push to master branch
     
     - The pipeline yml file has everything setup so far to build and test maven project 
     - `       env:
         TESTNG_FILE: 'testng.xml'
       run: mvn clean install -DsuiteXmlFile=$TESTNG_FILE
     This is actually taking which testng file to execute and based upon the env value for that particular step its executing
     tests for taht specific testng file.


## TODO
   1. Need to attach scenarios regarding Accessibility Testing with axe-core and Selenium
   2. Need to attach allure reporting on github actions



