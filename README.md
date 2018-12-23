# loan-service

### Wystawione usługi:

1.  applyForLoan: `localhost:8080/loan/apply`

Request POST:
```js
{
	"amount" : 7000,
	"term" : "2018-12-22T10:24"
}
```

2. ExtendLoan -`localhost:8080/loan/{applicationLoanId}/extend`
Request POST
body - empty


### Dostęp do bazy in memory

http://localhost:8080/h2-console/login.do

url:  jdbc:h2:mem:testdb

### Mechanizm reguł biznesowych

Mechanizm został stworzony zgodnie z zasadą -  otwarte na rozszerzenie, ale zamknięte na modyfikacje.
Dodałem hardcore, który można łatwo podmienić przez parametry np. z bazy, pliku.
```java
{
com.okomski.loanservice.validators.LoanApplicationValidationContext#VALIDATION_RULE_NAME
}
```
