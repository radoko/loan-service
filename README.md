# loan-service

Wystawione są dwie usługi:

1.  applyForLoan
```
  localhost:8080/loan/apply
```

Request POST:
```
{
	"amount" : 7000,
	"term" : "2018-12-22T10:24"
}
```

ExtendLoan - POST
```
localhost:8080/loan/{applicationLoanId}/extend
```

## Informację dodatkowe:

Dostęp do bazy in memory:
http://localhost:8080/h2-console/login.do

url:  jdbc:h2:mem:testdb


Z ważniejszych rzeczy - dynamiczny mechanizm do regułek biznesowych (obecnie hardcode)
```java
{
com.okomski.loanservice.validators.LoanApplicationValidationContext#VALIDATION_RULE_NAME
}
```
Łatwo do zastąpienia przez wartość z configa.
