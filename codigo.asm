.model tiny
.stack
.data
const equ db 1
constante equ 1
con equ 2
var1 db ?
var2 db ?
.code
.startup
call function
;for17
mov cx,0
mov al,var3
for17:
;Multiplicación
mov al,1
mul al,1
mov al,var1
;División
mov al,1
div al,1
mov al,var2
inc cx 
cmp cx,al
j1 for17
;if13:
mov al,2
cmp al,3
je if13
;Resta
mov al,1
sub al,1
mov al,var2
if13:
;División
mov al,1
div al,1
mov al,var2
end if13
.exit
function proc [near]
;Resta
mov al,1
sub al,1
mov al,const
function endp
.end

