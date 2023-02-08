---
output: pdf_document
geometry: paperheight=11in,paperwidth=8.5in,margin=1in
titlepage: true
# Ici : Sigle, Titre cours, Titre travail
title: |
  |
  |
  |
  | Département de génie informatique et génie logiciel
  |
  |
  |
subtitle: |
  | LOG1410
  | Analyse et conception de logiciels
  |
  |
  |
  |
  |  Laboratoire no. 1
  |
  |
  |
  |
author: |
  | Gabriel Bruyere (2248817) 
  | Nils Coulier (2077378)
  | Olivier Tremblay-Noël (1903926)
  |
  | 
  |
  |
  | Section de labo 03
  |
  |
  |
  |
date:  |
  | Hiver 2023
toc: true
toc-title: Table des matières # new 
toc-depth: 3
include-before: '`\newpage{}`{=latex}' # Page break after title page
header-includes: # Deactivate page numbers for Title page and TOC
 - \pagenumbering{gobble}
 - \usepackage{titling}
 - \usepackage{graphicx}
 - \pretitle{\begin{center}\LARGE\includegraphics[width=10cm]{logopoly.png}\\[\bigskipamount]}
 - \posttitle{\end{center}}
---
\newpage <!-- new page after TOC -->
\pagenumbering{arabic} <!-- Put page number back starting from body-->
