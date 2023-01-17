pandoc `
./titlepage_and_config.md `
./body.md `
./body2.md `
--pdf-engine="C:\Program Files\MiKTeX\miktex\bin\x64\xelatex.exe" `
-o output.pdf

Write-Output done!
