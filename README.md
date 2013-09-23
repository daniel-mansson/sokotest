sokotest
========

A debugging tool for sokoban solvers. This tool is meant to be run side-by-side with a debugger and will 
be able to visualize the state of the sokoban game as you step through the code.

sokotest reads sokoban state entries from stdin or by polling a file (when started with '-file filename').

--------

Using windows
Since named pipes are complicated in windows, it is recommended to use the '-file' flag. 
Start sokotest with the argument "-file filename" and configure your IDE to pipe stdout to that file.
Remember to flush the stream after each entry.

Using linux
Create a named pipe using mkfifo. Start sokotest using the new pipe as input. Configure your IDE to write to that pipe.

--------

This is the expected format for an entry (without % and the text following it, comments like this is not supported).
The order of the fields between begin and end is not specified. There can be multiple markings, using different IDs.

```
;begin          % Marks the beginning of an entry
name of state
;player         % (optional) The player position. Required for valid paths.
x(int)
y(int)
;path           % (optional) A path from the player. Uses U R L D for directions.
RURULDD
;state          % ("optional") The map state (boxes, goals, walls). Uses the same format as the input files.
#######         % Ends with a line containing a single '-'.
#   . #
#     #
# $   #
#     #
#######
-
;marking              % (optional) Used to mark areas in the map
id(int)               % Should be 0-9 to make toggling work
r g b (int int int)   % Color
count (int)           % Number of markings
x y (int int)         % Position of the marking, repeated 'count' times
;end                  % Marks the end of an entry
```

--------

Example entry:

```
;begin      
Awesome example state
;player
1
4
;path         
RU
;state         
#######        
#   . #
#     #
# $   #
#     #
#######
-
;marking              
1             
255 0 0
4
1 1
1 4
5 1
5 4
;marking              
2          
0 255 0
4
1 3
2 4
2 2
3 3
;end           
```
