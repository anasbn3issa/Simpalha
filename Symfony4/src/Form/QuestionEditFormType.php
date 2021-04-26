<?php


namespace App\Form;


use App\Entity\Question;
use App\Entity\Answer;
use Doctrine\ORM\EntityRepository;
use Symfony\Bridge\Doctrine\Form\Type\EntityType;
use Symfony\Component\Form\AbstractType;
use Symfony\Component\Form\Extension\Core\Type\TextType;
use Symfony\Component\Form\FormBuilderInterface;
use Symfony\Component\OptionsResolver\OptionsResolver;

class QuestionEditFormType extends AbstractType
{
    public function buildForm(FormBuilderInterface $builder, array $options)
    {
        $question = $options['question'];

        $builder
            ->add('question',TextType::class,[
                'help' => 'The question you want to add',
            ])
            ->add('rightAnswer',EntityType::class,[
                'help' => 'The right answer for your question',
                'class' => Answer::class,
                'query_builder' => function(EntityRepository $er) use ($question) {
                    return $er->createQueryBuilder('u')
                        ->andWhere('u.question = :questionId')
                        ->setParameter('questionId', $question->getId());
                },
                'choice_label' => function(Answer $answer){
                    return $answer->getSuggestion();
                }
            ])
        ;
    }

    public function configureOptions(OptionsResolver $resolver)
    {
        $resolver->setDefaults([
            'data_class' => Question::class,
        ]);

        $resolver->setRequired(['question']);
    }
}