--polo unit test
set serveroutput on

declare
   tmp varchar2(1000);
   tmp1 varchar2(1000);
   tmp2 varchar2(1000);
   tmp3 varchar2(1000);
   test_func varchar2(1000);
   test_func_case varchar2(1000);
   
   in_sales_chanl           VARCHAR2(1000);
   in_prod_type             VARCHAR2(1000);
   in_style                 VARCHAR2(1000);
   in_usage                 VARCHAR2(1000);
   in_gold_type             VARCHAR2(1000);
   in_wh_cost               NUMBER;
   in_wh_mu_base_amt        NUMBER;
   in_exch_rte              NUMBER;
   in_trf_fctr              NUMBER;
   in_model_seq_nbr         NUMBER;
   in_labor_cost            NUMBER;
   in_gold_cost             NUMBER;
   in_matrl_cost            NUMBER;
   in_indirct_cost          NUMBER;
   in_invnt_id              iv_non_item_pck.invnt_id%TYPE;
   out_wh_trf_price         NUMBER;
   out_pos_trf_price        NUMBER;
   out_pos_mu_base_amt      NUMBER;
   out_pos_price            NUMBER;
   out_markup               NUMBER;
   out_adj                  NUMBER;
   out_price_cde            VARCHAR2(1000);
   out_adj_ind              VARCHAR2(1000);
   out_sell_exp             NUMBER;
   out_no_disc_ind          VARCHAR2(1000);
begin
   dbms_output.put_line('.');
   --compare clac price
   --pk_price_lib.pl_calc_price_bf_round
   test_func := 'pk_price_lib.pl_calc_price_bf_round';
   
   --Case 1
   test_func_case := 'Case 1';
   FOR rec IN (
      SELECT wip_pck_id   
      FROM wip_pck a
      WHERE actv_ind = 'Y'  
      AND prod_type = 'DI' 
      AND EXISTS(SELECT * FROM wip_pck_qc_chk_list WHERE qc_type = '19' AND wip_pck_id = a.wip_pck_id)   
   ) LOOP
      BEGIN
      
         SELECT 'FSCB' , a.prod_type, b.style_catg_nbr, a.usage, a.gold_type, 
         a.wip_invnt_cost, a.wip_mu_base_amt, 1, 1, a.model_seq_nbr, 
         a.wip_labor_cost, a.wip_gold_cost, a.wip_matrl_cost, a.wip_indirct_cost, a.wip_pck_id,
         a.price, pk_wip_qc_flib.f_get_wip_pos_price(a.wip_pck_id)
         INTO in_sales_chanl, in_prod_type, in_style, in_usage, in_gold_type, 
         in_wh_cost, in_wh_mu_base_amt, in_exch_rte, in_trf_fctr, in_model_seq_nbr, 
         in_labor_cost, in_gold_cost, in_matrl_cost, in_indirct_cost, in_invnt_id,
         tmp, tmp3
         FROM wip_pck a, iv_css_model b
         WHERE a.model_seq_nbr = b.model_seq_nbr
         and a.wip_pck_id = rec.wip_pck_id;
         
         pk_price_lib.pl_calc_price_bf_round( 
            in_sales_chanl, in_prod_type, in_style, in_usage, in_gold_type, 
            in_wh_cost, in_wh_mu_base_amt, in_exch_rte, in_trf_fctr, in_model_seq_nbr, 
            in_labor_cost, in_gold_cost, in_matrl_cost, in_indirct_cost, in_invnt_id, 
            out_wh_trf_price, out_pos_trf_price, out_pos_mu_base_amt, out_pos_price, out_markup, 
            out_adj, out_price_cde, out_adj_ind, out_sell_exp, out_no_disc_ind);
         
         SELECT pk_price_lib.f_round_price(out_pos_price) INTO tmp1 FROM DUAL;
         
         IF tmp1 <> tmp THEN
            dbms_output.put_line('DIFF : wip_pck_id:'||in_invnt_id||' - in_wh_cost:'||in_wh_cost||' - out_pos_price:'||out_pos_price||' - round(out_pos_price):'||tmp1||' - tmp:'||tmp||' - tmp3:'||tmp3); 
         END IF;
         
      EXCEPTION
         WHEN OTHERS THEN
            dbms_output.put_line('SQL Error : '||test_func||' - '||test_func_case); 
      END;
   END LOOP;
      
   dbms_output.put_line('.');
end;
/