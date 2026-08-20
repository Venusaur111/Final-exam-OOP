INSERT INTO account (account_type) VALUES
('STANDARD'),
('PREMIUM'),
('GOLD');

INSERT INTO transaction (transaction_type, amount, account_id, reason) VALUES
('IN', 1500.00, (SELECT id FROM account WHERE account_type = 'STANDARD' LIMIT 1), 'Dépôt initial'),
('OUT', 200.50, (SELECT id FROM account WHERE account_type = 'STANDARD' LIMIT 1), 'Achat en ligne'),
('IN', 5000.00, (SELECT id FROM account WHERE account_type = 'PREMIUM' LIMIT 1), 'Virement salaire'),
('OUT', 120.00, (SELECT id FROM account WHERE account_type = 'GOLD' LIMIT 1), 'Abonnement mensuel');