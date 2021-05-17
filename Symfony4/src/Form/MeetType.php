<?php

namespace App\Form;

use App\Entity\Disponibilite;
use App\Entity\Feedback;
use App\Entity\Meet;
use App\Entity\Users;
use App\Repository\DisponibiliteRepository;
use App\Repository\UserRepository;
use Doctrine\ORM\EntityRepository;
use Symfony\Bridge\Doctrine\Form\Type\EntityType;
use Symfony\Component\Form\AbstractType;
use Symfony\Component\Form\Extension\Core\Type\SubmitType;
use Symfony\Component\Form\Extension\Core\Type\TextType;
use Symfony\Component\Form\FormBuilderInterface;
use Symfony\Component\OptionsResolver\OptionsResolver;

class MeetType extends AbstractType
{
    public function buildForm(FormBuilderInterface $builder, array $options)
    {

        $builder->add('disponibilite', EntityType::class,
            [
                'class' => Disponibilite::class,
                'attr'=>['class'=>'form-control'],
                'choice_label' => function (Disponibilite $disponibilite) {
                    return $disponibilite->getDatedeb()->format('d-m-y h:i')
                        . '->' . $disponibilite->getDatefin()->format('d-m-y h:i');
                },
                'query_builder' => function(DisponibiliteRepository  $r) use($options) {
                    if($options['disp'] != null)
                        return $r->getHelperDisp($options['disp'], $options['helper']);

                    return $r->getDisp($options['helper']);
                }
            ]);
        $builder->add('Save', SubmitType::class,[
            'attr'=>['class' => 'btn btn-primary waves-effect waves-light'],
        ]);
    }

    public function configureOptions(OptionsResolver $resolver)
    {
        $resolver->setDefaults([
            'data_class' => Meet::class,
            'disp' => Disponibilite::class,
            'helper' => Users::class
        ]);
    }
}
