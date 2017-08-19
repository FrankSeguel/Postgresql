
/**
 * Author:  fseguel
 * Created: 19-08-2017
 */

/**
 * Tabla
 * 
 */
CREATE TABLE public.parametros (
                codigo VARCHAR NOT NULL,
                valor VARCHAR NOT NULL,
                descripcion VARCHAR NOT NULL,
                dominio VARCHAR NOT NULL,
                CONSTRAINT parametros_pk PRIMARY KEY (codigo)
);


/**
 * FUNCTION
 * 
 */

CREATE OR REPLACE FUNCTION public.get_lst_parametros(
    IN p_dominio character varying,
    IN p_count integer DEFAULT 0,
    OUT p_cod_err integer,
    OUT p_desc_err character varying,
    OUT ref refcursor)
  RETURNS record AS
$BODY$
BEGIN
	
    IF p_dominio IS NOT NULL THEN
        OPEN ref FOR SELECT codigo, 
                        valor, 
                        descripcion 
                FROM parametros 
                WHERE dominio = p_dominio;

        p_cod_err := 0;
        p_desc_err := 'Success';	
    ELSE
        OPEN ref FOR SELECT codigo, 
                        valor, 
                        descripcion 
                FROM parametros;

        p_cod_err := 0;
        p_desc_err := 'Success';
    END IF;

END $BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION public.get_lst_parametros(character varying, integer)
  OWNER TO postgres;


/*
Ejemplo de la ejecucion o test.
*/

select public.get_lst_parametros('',1);