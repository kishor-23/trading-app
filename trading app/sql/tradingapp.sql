--buy stock procedure 


DELIMITER //

CREATE PROCEDURE buyStockProcedure(
    IN p_userId INT,
    IN p_stockId INT,
    IN p_quantity INT,
    IN p_price DECIMAL(15, 2),
    OUT p_result INT
)
BEGIN
    DECLARE userBalance DECIMAL(10, 2);
    DECLARE totalCost DECIMAL(15, 2);
    DECLARE existingQuantity INT DEFAULT 0;
    DECLARE existingTotalCost DECIMAL(15, 2) DEFAULT 0;

    -- Get the user's current balance
    SELECT balance INTO userBalance FROM users WHERE id = p_userId;

    -- Calculate the total cost of the purchase
    SET totalCost = p_quantity * p_price;

    -- Check if the user has enough balance
    IF userBalance < totalCost THEN
        SET p_result = 0; -- Insufficient balance
    ELSE
        -- Deduct the total cost from the user's balance
        UPDATE users SET balance = balance - totalCost WHERE id = p_userId;

        -- Check if the stock already exists in the user's portfolio
        SELECT quantity, total_cost INTO existingQuantity, existingTotalCost
        FROM portfolio
        WHERE user_id = p_userId AND stock_id = p_stockId;

        -- If the stock exists, update the quantity and total cost
        IF existingQuantity > 0 THEN
            SET existingQuantity = existingQuantity + p_quantity;
            SET existingTotalCost = existingTotalCost + totalCost;
            UPDATE portfolio
            SET quantity = existingQuantity,
                total_cost = existingTotalCost,
                avg_cost = existingTotalCost / existingQuantity
            WHERE user_id = p_userId AND stock_id = p_stockId;
        ELSE
            -- If the stock does not exist, insert a new record
            INSERT INTO portfolio (user_id, stock_id, quantity, avg_cost, total_cost)
            VALUES (p_userId, p_stockId, p_quantity, p_price, totalCost);
        END IF;

        -- Record the transaction
        INSERT INTO transactions (user_id, stock_id, shares, price, transaction_type)
        VALUES (p_userId, p_stockId, p_quantity, p_price, 'buy');

        SET p_result = 1; -- Success
    END IF;
END //

DELIMITER ;

-- sell stock procedure 
DELIMITER //

CREATE PROCEDURE sellStockProcedure(
    IN p_userId INT,
    IN p_stockId INT,
    IN p_quantity INT,
    IN p_price DECIMAL(15, 2),
    OUT p_result INT,
    OUT p_profit_loss DECIMAL(15, 2)
)
BEGIN
    DECLARE totalEarning DECIMAL(15, 2);
    DECLARE existingQuantity INT DEFAULT 0;
    DECLARE existingTotalCost DECIMAL(15, 2) DEFAULT 0;
    DECLARE currentStockPrice DECIMAL(15, 2);
    DECLARE buyedStockPrice DECIMAL(15, 2);

    -- Calculate the total earning from selling the stocks
    SET totalEarning = p_quantity * p_price;

    -- Check if the user has enough stocks to sell
    SELECT quantity, total_cost / quantity INTO existingQuantity, buyedStockPrice
    FROM portfolio
    WHERE user_id = p_userId AND stock_id = p_stockId;

    IF existingQuantity >= p_quantity THEN
        -- Calculate profit or loss based on the difference between the selling price and the buyed stock price
        SET p_profit_loss = (p_price - buyedStockPrice) * p_quantity;

        -- Add the earning to the user's balance
        UPDATE users SET balance = balance + totalEarning WHERE id = p_userId;

        -- Deduct the sold stocks from the portfolio
        UPDATE portfolio
        SET quantity = quantity - p_quantity,
            total_cost = total_cost - (buyedStockPrice * p_quantity)
        WHERE user_id = p_userId AND stock_id = p_stockId;

        -- If quantity becomes zero, delete the record from portfolio
        DELETE FROM portfolio
        WHERE user_id = p_userId AND stock_id = p_stockId AND quantity = 0;

        -- Record the transaction
        INSERT INTO transactions (user_id, stock_id, shares, price, transaction_type, profit_loss)
        VALUES (p_userId, p_stockId, p_quantity, p_price, 'sell', p_profit_loss);

        SET p_result = 1; -- Success
    ELSE
        SET p_result = 0; -- Insufficient stocks to sell
    END IF;
END //

DELIMITER ;

-- trigger to change stock price when buy or sell for simplicity when stock is buy it price will increase by 10 and decrease 10 if sell.

DELIMITER //

CREATE TRIGGER adjust_stock_price AFTER INSERT ON transactions
FOR EACH ROW
BEGIN
    DECLARE price_change DECIMAL(15, 2);

    -- Calculate the price change based on the transaction type
    IF NEW.transaction_type = 'buy' THEN
        SET price_change = 10.00;
    ELSE
        SET price_change = -10.00;
    END IF;

    -- Update the stock price
    UPDATE stocks
    SET current_stock_price = current_stock_price + price_change
    WHERE stock_id = NEW.stock_id;
END //

DELIMITER ;


