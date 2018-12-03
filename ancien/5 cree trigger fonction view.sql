CREATE TRIGGER trigger_sauvegarde_temp_view
  AFTER INSERT
  ON public.raw
  FOR EACH ROW
  EXECUTE PROCEDURE public.sauvegarde_temp_view();
