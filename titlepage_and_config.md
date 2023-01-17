---
output: pdf_document
geometry: paperheight=11in,paperwidth=8.5in,margin=1in

titlepage: true
# Ici : Sigle, Titre cours, Titre travail
title: |
  | École Polytechnique de Montréal
  | Département de génie informatique et génie logiciel
subtitle: |
  |
  | LOG2420
  | Analyse et conception d'interfaces utilisateurs
  |
  |  Travail pratique no. 1
author: |
  | Olivier Tremblay-Noël (1903926)
  | Author 2
  |
  | Section de labo 5, équipe 07
date: Septembre 2022

toc: true
toc-title: Table des matières # new 
toc-depth: 3

include-before: '`\newpage{}`{=latex}' # Page break after title page

header-includes: # Deactivate page numbers for Title page and TOC
 - \pagenumbering{gobble}

---
\newpage <!-- new page after TOC -->
\pagenumbering{arabic} <!-- Put page number back starting from body-->
