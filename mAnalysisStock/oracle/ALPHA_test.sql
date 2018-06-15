--polo unit test
set serveroutput on

declare
   tmp varchar2(1000);
   tmp1 varchar2(1000);
   tmp2 varchar2(1000);
   tmp3 varchar2(1000);
   test_func varchar2(1000);
   test_func_case varchar2(1000);
begin
   dbms_output.put_line('.');
      
   --PK_JW_MATRL_LIB.F_CHK_HKJSL_CERT_NBR Test
   --1: 以KJ英文字母開首 + 5位數字 (例:KJ12345(HKJSL))
   --2: 以KJ英文字母開首 + 5位數字 + ‘[‘ + 1位數字 + ‘-‘ + 1位數字 + ‘]’ (例:KJ12345[1-2] (HKJSL))
   --3: 以KJ-A英文字母開首 + 5位數字 (例:KJ-A12345(HKJSL))
   --4: 以SJ英文字母開首 + 6位數字 (例:SJ123456(HKJSL))
   test_func := 'PK_JW_MATRL_LIB.F_CHK_HKJSL_CERT_NBR';
   
   --Case 1
   test_func_case := 'Case 1';
   SELECT PK_JW_MATRL_LIB.F_CHK_HKJSL_CERT_NBR('KJ12345(HKJSL)') INTO tmp FROM DUAL;
   IF tmp <> 'Y' THEN
      dbms_output.put_line('Error : '||test_func||' - '||test_func_case); 
   END IF;

   --Case 2
   test_func_case := 'Case 2';
   SELECT PK_JW_MATRL_LIB.F_CHK_HKJSL_CERT_NBR('KJ12345[1-2](HKJSL)') INTO tmp FROM DUAL;
   IF tmp <> 'Y' THEN
      dbms_output.put_line('Error : '||test_func||' - '||test_func_case); 
   END IF;

   --Case 3
   test_func_case := 'Case 3';
   SELECT PK_JW_MATRL_LIB.F_CHK_HKJSL_CERT_NBR('KJ-A12345(HKJSL)') INTO tmp FROM DUAL;
   IF tmp <> 'Y' THEN
      dbms_output.put_line('Error : '||test_func||' - '||test_func_case); 
   END IF;
   
   --Case 3.1
   --test 錯誤 case 
   test_func_case := 'Case 3.1';
   SELECT PK_JW_MATRL_LIB.F_CHK_HKJSL_CERT_NBR('KJ2345(HKJSL)') INTO tmp FROM DUAL;
   IF tmp = 'Y' THEN
      dbms_output.put_line('Error : '||test_func||' - '||test_func_case); 
   END IF;

   --Case 4
   test_func_case := 'Case 4';
   SELECT PK_JW_MATRL_LIB.F_CHK_HKJSL_CERT_NBR('SJ123456(HKJSL)') INTO tmp FROM DUAL;
   IF tmp <> 'Y' THEN
      dbms_output.put_line('Error : '||test_func||' - '||test_func_case); 
   END IF;
   
   --Case 5
   --test 錯誤 case 
   test_func_case := 'Case 5';
   SELECT PK_JW_MATRL_LIB.F_CHK_HKJSL_CERT_NBR('SJ1123456(HKJSL)') INTO tmp FROM DUAL;
   IF tmp = 'Y' THEN
      dbms_output.put_line('Error : '||test_func||' - '||test_func_case); 
   END IF;
   
   --Case 6
   --test 錯誤 case 
   test_func_case := 'Case 6';
   SELECT PK_JW_MATRL_LIB.F_CHK_HKJSL_CERT_NBR('S112456(HKJSL)') INTO tmp FROM DUAL;
   IF tmp = 'Y' THEN
      dbms_output.put_line('Error : '||test_func||' - '||test_func_case); 
   END IF;
   
   --Case 7
   --test 錯誤 case 
   test_func_case := 'Case 7';
   SELECT PK_JW_MATRL_LIB.F_CHK_HKJSL_CERT_NBR('SJ12345A(HKJSL)') INTO tmp FROM DUAL;
   IF tmp = 'Y' THEN
      dbms_output.put_line('Error : '||test_func||' - '||test_func_case); 
   END IF;
   
   dbms_output.put_line('.');
end;
/