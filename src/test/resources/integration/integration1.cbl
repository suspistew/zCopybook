01 CLIENT.
    03 CLIENT-IMAGE.
        05  HEADER.
            08  TECH-HEADER.
                10  TYPE-IMAGE            PIC   X(8).
                10  VERSION               PIC   X(2).
                10  IMAGE-ORIGIN.
                    15  CODE-APPLICATION    PIC   X(28).
                    15  SERVER-ID           PIC   X(4).
                10  MESSAGE                 PIC   X(16).
                10  USER-ID.
                    15 NAME                 PIC   X(8).
                    15 FILLER               PIC   X(8).
                10  DATA-1                PIC   X(32).
                10  DATA-2                PIC   X(08).
                10  DATA-3                PIC   X(08).
                10  DATA-4                PIC   X(16).
                10  DATA-5                PIC   X(40).
                10  DATA-6                PIC   X(08).
                10  DATA-7                PIC   X(08).
                10  DATA-8                PIC   X(02).
                10  METADATA OCCURS 10.
                    15 NAME                 PIC   X(02).
                    15 TYPE                 PIC   X(04).
                    15 VALUE                PIC   X(80).
                    15 FILLER               PIC   X(12).
                10  FILLER               PIC X(14).
                10  LANGUAGE             PIC X(03).
                10  CANAL                PIC X(03).
                10  CODE-1               PIC X(02).
                10  TECHNICAL-MESSAGE    PIC X(30).
                10  IMAGE-TIMESTAMP      PIC X(26).
                10 SEQUENCE.
                    20 REF    PIC X(30).
                    20 TYPE    PIC X.
                        88 TYPE-1            VALUE 1.
                        88 TYPE-2            VALUE 2.
                        88 TYPE-3            VALUE 3.
                        88 TYPE-4            VALUE 0.
                    20 NUM   PIC X(4).
                10 ADDITIONNAL-DATA.
                    15 DATA OCCURS 5.
                        20 KEY      PIC X(3).
                        20 VALUE     PIC X(29).
                10  FILLER                PIC X(87).
            08  CONTEXT.
                10  INFO     PIC X(562).
        05 BODY.
            08 CONTRACT
                10 TYPE             PIC X(04).
                10 VERSION          PIC 9(03).
                10 TYPE-1           PIC X(01).
                10 TYPE-2           PIC X(01).
                10 TYPE-3           PIC X(01).
                10 NUM-CLIENT       PIC 9(10).
                10 INDICE-1         PIC X(03).
                10 NUM-CLIENT-2     PIC 9(10).
                10 INDICE-2         PIC X(03).
                10 NUM-CLI          PIC X(10).
                10 FILLER           PIC X(10).
                10 NUM-CONTRAT      PIC X(16).
                10 TIMESTAMP        PIC X(26).
                10 REGION           PIC X(04).
                10 COUNTRY          PIC X(04).
                10 STATUS           PIC X(08).
                10 TYPE-3           PIC X(01).
                10 FILLER                       PIC X(84).
            08 COMMON.
                10 COMMON-DATA.
                    13 STATE             PIC X(01).
                    13 PRD               PIC X(03).
                    13 STATE             PIC X(01).
                    13 STATE-UPDT-DATE   PIC X(08).
                    13 REASON-UPDT       PIC X(02).
                    13 CODE-1            PIC X(01).
                    13 CODE-2            PIC X(03).
                    13 CODE-3            PIC X(05).
                    13 CODE-4            PIC X(02).
                    13 A-DATE            PIC X(08).
                    13 B-DATE            PIC X(08).
                    13 C-DATE            PIC X(08).
                    13 D-DATE            PIC X(08).
                    13 E-DATE            PIC X(04).
                    13 INDICE-1           PIC X(05).
                    13 CODE-5            PIC X(01).
                    13 CODE-6            PIC X(01).
                    13 AMOUNT-MONNEY-MADE     PIC S9(09).
                    13 AMOUNT-BENEF      PIC S9(11).
                    13 INDICE-2       PIC X(01).
                    13 HISTORIQUE-TRANS OCCURS 5.
                        15 TYPE         PIC X(04).
                        15 KEY        PIC X(01).
                        15 AMOUNT-MONNEY-MADE         PIC S9(11).
                        15 AMOUNT-BENEF        PIC S9(11).
                    13 ID-RANDOM       PIC 9(05).
                    13 MESSAGES OCCURS 15.
                        15 VALUE         PIC X(05).
                    13 TYPE-CLIENT-RISK      PIC X(05).
                    13 IDENTIFICATION       PIC X(30).
                    13 CODE-7            PIC X(01).
                    13 FILLER                          PIC X(243).
                    13 FILLER       PIC X(500).
            08 INFOS.
                10 TYPE                             PIC X(01).
                10 FILLER                           PIC X(199).
            08 FILLER                               PIX X(3323).
