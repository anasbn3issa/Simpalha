$(document).ready(function() {

    // question ids of the questions in the Quiz
    var questionIds = function(){
        let questions = [];
        let i = 1;
        $(".question-counter").each(function(){

            let questionId = parseInt($("#question-counter-"+i).val());
            questions.push(questionId);
            i++;
        })

        return questions;
    }

    // answer ids for chosen answer in the Quiz
    var answerIds = function(){
        let answers = [];
        let i = 1;
        $(".answer-counter").each(function(){
            if($("#answer-"+i).is(':checked')){
                console.log($("#answer-"+i));
                console.log("answer counter : "+$("#answer-"+i).val());
                answers.push($("#answer-"+i).val());
            }
            i++
        });

        return answers;
    }

    // data processing after "show result" is clicked in the Quiz page
    $('.js-quiz-submit').click( function(e) {

        e.preventDefault();

        var $link = $(e.currentTarget);

        let questions = questionIds();
        let selectedAnswers = answerIds();


        $.ajax({
            method: 'POST',
            dataType: "json",
            url: $link.attr('href'),
            data:{
                'selectedAnswers' : selectedAnswers,
                'questions' : questions
            },
        }).done(function(data) {
            convertedAverage = data.convertedAverage;

            if(convertedAverage === -1){
                $('.result-modal-title').addClass('text-danger')
                $('.result-modal-title').html('Sorry..');
                $('.result-modal').addClass('text-dark');
                $('.result-modal').html("This Quiz has no questions yet");
            }
            else if(convertedAverage === -2)
            {
                $('.result-modal-title').addClass('text-danger')
                $('.result-modal-title').html('Try again later!');
                $('.result-modal').addClass('text-dark');
                $('.result-modal').html("You already have passed this test, try again Later");
            }
            else if(convertedAverage<5 && convertedAverage>=0)
            {
                $('.result-modal-title').addClass('text-danger')
                $('.result-modal-title').html('Try and put in just a little effort at least..');
                $('.result-modal').addClass('text-danger');
                $('.result-modal').html("Average : "+data.convertedAverage+ "/20");
            }
            else if(convertedAverage>=5 && convertedAverage<10){
                $('.result-modal-title').addClass('text-warning')
                $('.result-modal-title').html('I mean come on! Is this the best you can do? ');
                $('.result-modal').addClass('text-warning');
                $('.result-modal').html("Average : "+data.convertedAverage+ "/20");

            }
            else if(convertedAverage>=10 && convertedAverage<15){
                $('.result-modal-title').addClass('text-primary')
                $('.result-modal-title').html('Nice one! Strive to achieve higher marks next time!');
                $('.result-modal').addClass('text-primary');
                $('.result-modal').html("Average : "+data.convertedAverage+ "/20");

            }
            else{
                $('.result-modal-title').addClass('text-success')
                $('.result-modal-title').html('Aren\'t you the best? YOU ROCK!');
                $('.result-modal').addClass('text-success');
                $('.result-modal').html("Average : "+data.convertedAverage+ "/20");

            }
            $('#showResultModal').modal('show');
            console.log('those are the selected answers : ' + data.selectedAnswers);
            console.log('those are the questions : ' + data.questions);
            console.log('converted average : ' + data.convertedAverage);
        })
    });

    // datetime chosen in the form
    var chosenDate = function(){
        return $("#datetime-input").val();
    }


    $('.js-compare-average').click( function(e) {
        e.preventDefault();

        var $link = $(e.currentTarget);

        let selectedDate = chosenDate();
        console.log(chosenDate());

        $.ajax({
            method: 'POST',
            dataType: "json",
            url: $link.attr('href'),
            data:{
                'selectedDate' : selectedDate
            },
        }).done(function(data) {
            $compare = $(".compare-average");
            dataAverage = Math.round((data.average + Number.EPSILON) * 100) / 100;
            dataAverageDiff = Math.round((data.averageDiff + Number.EPSILON) * 100) / 100;

            if(data.averageDiff > 0)
            {
                $compare.addClass("text-success");
                $compare.html(dataAverage + " (+"+dataAverageDiff+")");
            }
            else if(data.averageDiff === 0){
                $compare.addClass("text-info");
                $compare.html(dataAverage + " (="+dataAverageDiff+")");
            }
            else if(data.averageDiff <0){
                $compare.addClass("text-danger");
                $compare.html(dataAverage + " ("+dataAverageDiff+")");

            }

        })
    });
});