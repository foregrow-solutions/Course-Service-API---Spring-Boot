(function () {


    var questions =[ {
        question: "What is 3*6?",
        choices: [3, 6, 9, 12, 18],
        correctAnswer: "18"
    }, {
        question: "What is 4*6?",
        choices: [3, 6, 9, 12, 24],
        correctAnswer: "24"
    },
    {
        question: "What is 4+6?",
        choices: [3, 6, 9, 10, 24],
        correctAnswer: "10"
    }];

    // {
    //     question: "What is 8*9 and 9*11?",
    //     choices: [72, 99, 108, 134, 156],
    //     type: "checkbox",
    //     correctAnswer: [72, 99]
    // },

    var questionCounter = 0; //Tracks question number
    var selections = []; //Array containing user choices
    var quiz = $('#quiz'); //Quiz div object

    // Display initial question
    // displayNext();

    // Click handler for the 'next' button
    $('#next').on('click', function (e) {
        e.preventDefault();

        // Suspend click listener during fade animation
        if (quiz.is(':animated')) {
            return false;
        }
        choose();
       var selection= selections[questionCounter];
console.log('myquestioncounter',selections[questionCounter])
        // If no user selection, progress is stopped
        if (!NaN(selection)) {
            alert('Please make a selection!');
        } else {
            questionCounter++;
            displayNext();
        }
    });

    // Click handler for the 'prev' button
    $('#prev').on('click', function (e) {
        e.preventDefault();

        if (quiz.is(':animated')) {
            return false;
        }
        choose();
        questionCounter--;
        displayNext();
    });

    // Click handler for the 'Start Over' button
    $('#start').on('click', function (e) {
        e.preventDefault();

        if (quiz.is(':animated')) {
            return false;
        }
        questionCounter = 0;
        selections = [];
        displayNext();
        $('#start').hide();
    });

    // Animates buttons on hover
    $('.button').on('mouseenter', function () {
        $(this).addClass('active');
    });
    $('.button').on('mouseleave', function () {
        $(this).removeClass('active');
    });

    // Creates and returns the div that contains the questions and 
    // the answer selections
    var questionType;
    function createQuestionElement(index) {
        var qElement = $('<div>', {
            id: 'question'
        });


        var header = $('<h2>Question ' + (index + 1) + ':</h2>');
        qElement.append(header);

        var question = $('<p>').append(questions[index].question);
        qElement.append(question);
        questionType = questions[index].type;
        var element;
        if (questionType === "radio") {
            element = createRadios(index);
        } else if (questionType === "checkbox") {
            element = createCheckBox(index);
        } else {
            element = createTextArea(index);
        }
        qElement.append(element);

        return qElement;
    }

    // Creates a list of the answer choices as radio inputs
    function createRadios(index) {
        var radioList = $('<ul>');
        var item;
        var input = '';
        var choices=questions[index].choices;
        for (var i = 0; i < choices.length; i++) {
            item = $('<li>');
            input = '<input type="radio" name="answer" value=' + choices[i] + ' />';
            input += questions[index].choices[i];
            item.append(input);
            radioList.append(item);
        }
        return radioList;
    }



    // Reads the user selection and pushes the value to an array
    function choose() {
            value = $('input[name="answer"]:checked').val();
            selections[questionCounter] = $('input[name="answer"]:checked').val();
    }

    displayNext();
    // Displays next requested element
    function displayNext() {

        quiz.fadeOut(function () {
            $('#question').remove();

            if (questionCounter < questions.length) {
                var nextQuestion = createQuestionElement(questionCounter);

                quiz.append(nextQuestion).fadeIn();

                if (!(isNaN(selections[questionCounter]))) {
                    console.log("inside selectins counter");

                    $('input[value=' + selections[questionCounter] + ']').prop('checked', true);

                }

                // Controls display of 'prev' button
                if (questionCounter === 1) {
                    $('#prev').show();
                } else if (questionCounter === 0) {

                    $('#prev').hide();
                    $('#next').show();
                }
            } else {
                var scoreElem = displayScore();
                quiz.append(scoreElem).fadeIn();
                $('#next').hide();
                $('#prev').hide();
                $('#start').show();
            }
        });
    }

    // Computes score and returns a paragraph element to be displayed
    function displayScore() {
        console.log('myselections',selections)
        var score = $('<p>', { id: 'question' });

        var numCorrect = 0;
        for (var i = 0; i < selections.length; i++) {

            if (selections[i] === questions[i].correctAnswer) {
                numCorrect++;
            }
        }

        score.append('You got ' + numCorrect + ' questions out of ' +
            questions.length + ' right!!!');
        return score;
    }
})();