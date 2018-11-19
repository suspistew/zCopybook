*****************************************************************
*                                                               *
*     GNAAAAAAAA                                                *
*                                                               *
*****************************************************************
01 CLIENT.
  03 CLIENT-COMMON-INFOS.
    05 FIRSTNAME                       PIC X(18).
    05 LASTNAME                        PIC X(12).
  03 CLIENT-INFOS-OTHER             REDEFINES CLIENT-COMMON-INFOS.
  03 SOMETHING                         PIC X(18).
  03 ELSE                              PIC X(18).
