# Trading app

#### Users Table

| id  | name | email | pancardno | phone | dob | password  | balance |
| --- | --- | --- | --- | --- | --- | --- | --- |
| 1   | John | john@mail.com | ABCDE1234F | 1234567890 | 1980-01-01 | pass123  | 10000.00 |

#### Stocks Table

| stock_id | symbol | company_name | current\_stock\_price | cap_category |
| --- | --- | --- | --- | --- |
| 1   | AAPL | Apple Inc. | 150.00 | Large |
| 2   | TSLA | Tesla Inc. | 200.00 | Large |

#### Portfolio Table (Before Transactions)

| portfolio_id | user_id | stock_id | quantity | buyed_price |
| --- | --- | --- | --- | --- |
| (empty) |     |     |     |     |

### Buy Scenario

#### Action: User John buys 10 shares of AAPL at $150 each.

1.  **Checking Balance**:
    
    * User John's current balance: $10,000.00
    * Total cost of purchase: 10 shares * $150 = $1,500.00
    * New balance: $10,000.00 - $1,500.00 = $8,500.00
2.  **Updating Portfolio**:
    
    * New entry added to the portfolio for John: 10 shares of AAPL at $150 each.
3.  **Recording Transaction**:
    
    * A new transaction record is created for the purchase.
4.  **Updating Stock Price**:
    
    * AAPL stock price increases by $10.00 due to the buy transaction.
    * New stock price for AAPL: $150.00 + $10.00 = $160.00

#### Users Table (After Buy)

| id  | name | email | pancardno | phone | dob | password  | balance |
| --- | --- | --- | --- | --- | --- | --- | --- |
| 1   | John | john@mail.com | ABCDE1234F | 1234567890 | 1980-01-01 | pass123 | 8500.00 |

#### Portfolio Table (After Buy)

| portfolio_id | user_id | stock_id | quantity | buyed_price |
| --- | --- | --- | --- | --- |
| 1   | 1   | 1   | 10  | 150.00 |

#### Transactions Table (After Buy)

| transaction_id | user_id | stock_id | shares | price | transaction_type | timestamp |
| --- | --- | --- | --- | --- | --- | --- |
| 1   | 1   | 1   | 10  | 150.00 | buy | 2024-06-18 12:00:00 |

### Sell Scenario

#### Action: User John sells 5 shares of AAPL at $160 each.

1.  **Checking Portfolio**:
    
    * John currently holds 10 shares of AAPL.
    * Quantity to sell: 5 shares
    * Remaining shares: 10 - 5 = 5 shares
2.  **Calculating Earnings**:
    
    * Total earnings from the sale: 5 shares * $160 = $800.00
    * Profit/Loss: ($160 - $150) * 5 shares = $50.00
3.  **Updating Balance**:
    
    * New balance: $8,500.00 + $800.00 = $9,300.00
4.  **Updating Portfolio**:
    
    * Update John's portfolio to reflect the sale.
    * New entry: 5 shares of AAPL at the original buyed price of $150 each.
5.  **Recording Transaction**:
    
    * A new transaction record is created for the sale.
6.  **Updating Stock Price**:
    
    * AAPL stock price decreases by $10.00 due to the sell transaction.
    * New stock price for AAPL: $160.00 - $10.00 = $150.00

#### Users Table (After Sell)

| id  | name | email | pancardno | phone | dob | password | balance |
| --- | --- | --- | --- | --- | --- | --- | --- |
| 1   | John | john@mail.com | ABCDE1234F | 1234567890 | 1980-01-01 | pass123 | 9300.00 |

#### Portfolio Table (After Sell)

| portfolio_id | user_id | stock_id | quantity | buyed_price |
| --- | --- | --- | --- | --- |
| 1   | 1   | 1   | 5   | 150.00 |

#### Transactions Table (After Sell)

| transaction_id | user_id | stock_id | shares | price | transaction_type | profit_loss | timestamp |
| --- | --- | --- | --- | --- | --- | --- | --- |
| 1   | 1   | 1   | 10  | 150.00 | buy | NULL | 2024-06-18 12:00:00 |
| 2   | 1   | 1   | 5   | 160.00 | sell | 50.00 | 2024-06-18 12:10:00 |

### Summary

* **Balance Changes**:
    
    * Initial Balance: $10,000.00
    * After Buy: $8,500.00
    * After Sell: $9,300.00
* **Portfolio Changes**:
    
    * Initial: Empty
    * After Buy: 10 shares of AAPL at $150 each
    * After Sell: 5 shares of AAPL at $150 each
* **Stock Price Changes**:
    
    * Initial: $150.00
    * After Buy: $160.00
    * After Sell: $150.00

This Markdown  demonstrates the detailed steps and effects of buying and selling stocks in the trading app.