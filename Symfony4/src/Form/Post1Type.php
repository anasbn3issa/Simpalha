<?php

namespace App\Form;

use App\Entity\Module;
use App\Entity\Post;
use Symfony\Bridge\Doctrine\Form\Type\EntityType;
use Symfony\Component\Form\AbstractType;
use Symfony\Component\Form\Extension\Core\Type\TextareaType;
use Symfony\Component\Form\FormBuilderInterface;
use Symfony\Component\OptionsResolver\OptionsResolver;
use Symfony\Component\Validator\Constraints\File;
use Symfony\Component\Form\Extension\Core\Type\FileType;

class Post1Type extends AbstractType
{
    public function buildForm(FormBuilderInterface $builder, array $options)
    {
        $builder
            ->add('module' ,EntityType::class, [
                'class'=>Module::class,
                'choice_label'=> function(Module $module){
                return sprintf('%s',$module->getName());
                },
                'placeholder' => 'Choose a module',
                'invalid_message' => 'Symfony is too smart for your hacking !'
            ])
            ->add('problem',TextareaType::class,[
                'help'=> 'Be as specific as possible ! '
                ]
            )
            ->add('imageName', FileType::class,[
                'label' => 'image',
                'mapped' => false,
                'required' => false,
                'constraints' => [
                    new File([
                        'maxSize' => '2048k',

                    ])
                ]
            ])
        ;
    }

    public function configureOptions(OptionsResolver $resolver)
    {
        $resolver->setDefaults([
            'data_class' => Post::class
        ]);
    }
}
