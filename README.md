JCommandPanel
=============

Java GUI component whitch simulates console behaviour

Features:

- allows simple GUI for typing textual commands and displaying custom application output

- customizable look

- built-in command history

Usage:

- Write a parser class whitch implements CommandParser interface and override parseCommand method

- Put JCommandPanel instance on your application frame and assign your parser instance to command panel