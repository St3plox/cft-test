CREATE OR REPLACE PROCEDURE redem_debt(pTYPE VARCHAR2, pSUMM NUMBER) IS
  CURSOR cur_debts IS
    SELECT rowid AS rid, DATE_B, S_IN, S_DEBT, S_REDEM
    FROM DEBTS
    WHERE TYPE = pTYPE
    ORDER BY DATE_B;

  vSUMM NUMBER := pSUMM;
  cumulative_debt NUMBER := 0;
  total_paid NUMBER := 0;
BEGIN
  FOR rec IN cur_debts LOOP
    DECLARE
      v_current_debt NUMBER;
      v_pay_now NUMBER;
      v_new_S_IN NUMBER;
      v_new_S_REDEM NUMBER;
    BEGIN
      IF rec.rid = 1 THEN
        cumulative_debt := rec.S_IN + rec.S_DEBT - rec.S_REDEM;
      ELSE
        cumulative_debt := cumulative_debt + rec.S_DEBT - rec.S_REDEM;
      END IF;
      
      IF vSUMM >= (cumulative_debt - total_paid) THEN
        v_pay_now := cumulative_debt - total_paid;
      ELSE
        v_pay_now := vSUMM;
      END IF;

      v_new_S_REDEM := rec.S_REDEM + v_pay_now;

      IF v_pay_now = (cumulative_debt - total_paid) THEN
        v_new_S_IN := 0;
      ELSE
        v_new_S_IN := cumulative_debt - total_paid - v_pay_now;
      END IF;

      UPDATE DEBTS
      SET S_IN = v_new_S_IN,
          S_REDEM = v_new_S_REDEM
      WHERE rowid = rec.rid;

      total_paid := total_paid + v_pay_now;
      vSUMM := vSUMM - v_pay_now;

      IF vSUMM <= 0 THEN
        EXIT;
      END IF;
    END;
  END LOOP;
  COMMIT;
END;
