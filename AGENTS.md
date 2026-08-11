# Agent Instructions & Guidelines for Speed Math

This document contains rules, architecture conventions, and project scope guidelines for AI assistants working on the Speed Math codebase.

## Project Scope
- Speed Math is a fast, 100% offline mental math practice tool.
- All question topics must have dedicated, high-quality question generators in `com.example.domain.generator.QuestionGenerator.kt`.
- Maintain clean MVVM separation:
  - `domain/`: Business logic, topic catalogs, question engines (`QuestionGenerator`).
  - `data/`: Local storage, DataStore preferences, Room entities.
  - `ui/`: Compose UI screens, components, and ViewModels.

## Topic Management Rules
- When adding or modifying topics, update both `TopicsCatalog.allTopics` and `QuestionGenerator.getGeneratorForTopic()`.
- Ensure question text, expressions, and step-by-step explanations are mathematically accurate and easy to read.
