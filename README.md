# microblog
Team project on WWSIS created by Jolanta Kulik, Krzysztof Kogut, Krzysztof Chruściel, Konrad Boć-Orzechowski.

How to start project:
1.
2.

How git flow looks like:
1. Create new branch from develop -> The name should contain what we are working on. Examples: fix/task_name for defects and feature/task_name for new improvements.
    - git checkout -b feature/NEW_BRANCH_NAME
2. Before create a PR(pull-request):
    - git checkout develop
    - git pull
    - git checkout CURRENT_BRANCH
    - git merge develop
3. Assign at least 2 reviewers to review.
4. If the PR is accepted, developer should merge a PR. If no, make the appropriate changes and notify reviewer.


