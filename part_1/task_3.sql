CREATE OR REPLACE PROCEDURE redem_debt(
  pTYPE VARCHAR2,
  pSUMM NUMBER
)
IS
  CURSOR c IS
    SELECT ROWID rid, date_b, s_in, s_debt, s_redeem
      FROM DEBTS
     WHERE TYPE = pTYPE
     ORDER BY DATE_B;
  
  v_summ_left NUMBER := pSUMM;
BEGIN
  FOR rec IN c LOOP
    IF v_summ_left <= 0 THEN
      EXIT;
    END IF;
    
    -- сумма текущего долга
    v_current_debt := rec.s_in + rec.s_debt + rec.s_redeem;
    
    -- доля оплаты (сумма, которую мы можем покрыть)
    v_to_pay := LEAST(v_summ_left, v_current_debt);
    
    -- сколько останется долга
    v_new_s_in := GREATEST(0, rec.s_in - v_to_pay);
    
    -- пересчитаем, сколько ещё осталось после оплаты
    v_summ_left := v_summ_left - (rec.s_in - v_new_s_in);
    
    -- если оплатили больше, остаток идёт на текущий долг
    IF v_summ_left > 0 THEN
      v_new_s_redeem := GREATEST(0, rec.s_redeem - v_summ_left);
      v_summ_left := v_summ_left - (rec.s_redeem - v_new_s_redeem);
    ELSE
      v_new_s_redeem := rec.s_redeem;
    END IF;

    UPDATE DEBTS
       SET S_IN = v_new_s_in,
           S_REDEM = v_new_s_redeem
     WHERE ROWID = rec.rid;
    
  END LOOP;
  
  COMMIT;
END;
/
