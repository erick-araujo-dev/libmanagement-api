-- V1_add_unique_constraint_to_email_column.sql

-- Adicionar restrição UNIQUE à coluna de e-mail na tabela de usuário
ALTER TABLE user
ADD CONSTRAINT email_unique UNIQUE (email);
