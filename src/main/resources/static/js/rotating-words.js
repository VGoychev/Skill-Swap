(function () {
    /**
     * Rotates through a list of words on a target element with fade transitions.
     * @param {string} elementId - The ID of the element whose text should rotate.
     * @param {string[]} words - The list of words to cycle through.
     * @param {number} interval - How often (ms) to change words.
     * @param {number} fadeDuration - How long (ms) the fade-out animation lasts.
     */
    function setupRotatingText(elementId, words) {
        const el = document.getElementById(elementId);
        if (!el || !Array.isArray(words) || !words.length) return;

        let prev = -1;
        function pickRandomIndex() {
            if (words.length === 1) return 0;
            let i = Math.floor(Math.random() * words.length);
            while (i === prev) {
                i = Math.floor(Math.random() * words.length);
            }
            prev = i;
            return i;
        }

        el.textContent = words[pickRandomIndex()];

        function rotateWord() {
            el.classList.add('fade-out');
            setTimeout(() => {
                el.textContent = words[pickRandomIndex()];
                el.classList.remove('fade-out');
            }, 400);
        }

        setInterval(rotateWord, 2500);
    }



    window.setupRotatingText = setupRotatingText;
})();
