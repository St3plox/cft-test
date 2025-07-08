CREATE OR REPLACE FUNCTION get_min(
  pVal1 NUMBER := NULL,
  pVal2 NUMBER := NULL,
  pVal3 NUMBER := NULL
) RETURN NUMBER
IS
  res NUMBER := NULL;
BEGIN
  IF pVal1 IS NOT NULL THEN
    res := pVal1;
  END IF;
  
  IF pVal2 IS NOT NULL THEN
    IF res IS NULL OR pVal2 < res THEN
      res := pVal2;
    END IF;
  END IF;

  IF pVal3 IS NOT NULL THEN
    IF res IS NULL OR pVal3 < res THEN
      res := pVal3;
    END IF;
  END IF;

  RETURN res;
END;
/

