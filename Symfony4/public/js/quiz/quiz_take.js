$(document).ready(function() {

    var questionIds = function(){
        let questions = [];
        let i = 1;
        $(".question-counter").each(function(){
            console.log($("#question-counter-"+i));
            console.log("Question counter : "+$("#question-counter-"+i).val());
            console.log(i);
            let questionId = parseInt($("#question-counter-"+i).val());
            questions.push(questionId);
            i++;
        })

        return questions;
    }

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

    $('.js-quiz-submit').click( function(e) {
        e.preventDefault();
        console.log('this is e : ' + e);

        var $link = $(e.currentTarget);
        console.log('this is e link : ' + e.currentTarget);

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

            if(convertedAverage<5)
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
});