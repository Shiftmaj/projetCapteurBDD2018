Create or Replace function archive_params()
RETURNS trigger

AS
$BEGIN$
Begin
INSERT INTO params_journal values (
(SELECT hours FROM params),
(SELECT intakes FROM params),
(SELECT over FROM params),
 (SELECT under FROM params),
 (SELECT id FROM params));

DELETE from params;
 End
$BEGIN$
 LANGUAGE plpgsql