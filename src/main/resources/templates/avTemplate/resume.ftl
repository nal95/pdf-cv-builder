<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bulma@0.9.4/css/bulma.min.css">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bulma-timeline@3.0.5/dist/css/bulma-timeline.min.css">
    <link href="https://cdn.jsdelivr.net/npm/remixicon@4.2.0/fonts/remixicon.css" rel="stylesheet"/>
    <link rel="stylesheet" href="style.css">
    <title>${user.firstName} ${user.lastName} - Curriculum Vitae</title>
</head>

<body>
<div class="container">
    <section class="section">
        <div class="box">
            <div class="columns is-2">

                <!-- first part -->
                <div class="column is-4">

                    <!-- Summary -->
                    <fieldset class="box summary">
                        <div class="block">
                            <div class="block-content">
                                <div class="media">
                                    <div class="media-left">
                                        <figure class="image is-128x128">
                                            <img class="is-rounded" src="image/${user.id}"
                                                 alt="Placeholder image">
                                        </figure>
                                    </div>
                                    <div class="media-content">
                                        <p>${user.summary}</p>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </fieldset>

                    <!-- Education -->
                    <fieldset class="box education pt-0">
                        <legend class="tag is-info is-medium">Education</legend>
                        <div class="content">
                            <ul>
                                <#list resume.educations as education>
                                    <li class="heading"><strong>${education.field}</strong></li>
                                    <div class="columns is-2 is-size-7">
                                        <div class="column">
                                            <p class="column-content has-text-left">
                                                <span>${education.degree} in ${education.school}</span>
                                            </p>
                                        </div>
                                        <div class="column has-text-right is-size-7">
                                            <p>${education.startDate} - ${education.endDate}</p>
                                        </div>
                                    </div>
                                </#list>
                            </ul>
                        </div>
                    </fieldset>

                    <!-- Technical Experiences -->
                    <fieldset class="box technical-experiences pt-2">
                        <legend class="tag is-info is-medium">Technical Experiences</legend>
                        <table class="table  is-narrow is-fullwidth">
                            <thead>
                            <tr>
                                <th>Category</th>
                                <th>Skill</th>
                                <th>Level</th>
                            </tr>
                            </thead>
                            <tbody class="is-vcentered has-text-centered">
                            <#list resume.technicalExperiences as technicalExperience>
                                <tr>
                                    <td class="is-vcentered has-text-centered"
                                        rowspan="5">${technicalExperience.topic}</td>
                                    <#list technicalExperience.details as detail>
                                        <#if detail?counter == 1>
                                            <td class="is-vcentered has-text-centered">${detail.name}</td>
                                            <td class="is-vcentered has-text-centered">
                                                <progress class="progress is-info is-small" value="${detail.level}"
                                                          max="100"></progress>
                                            </td>
                                        </#if>
                                    </#list>
                                </tr>
                                <#list technicalExperience.details as detail>
                                    <#if detail?counter != 1>
                                        <tr>
                                            <td class="is-vcentered has-text-centered">${detail.name}</td>
                                            <td class="is-vcentered has-text-centered">
                                                <progress class="progress is-info is-small" value="${detail.level}"
                                                          max="100"></progress>
                                            </td>
                                        </tr>
                                    </#if>
                                </#list>
                            </#list>
                            <tr></tr>
                            </tbody>
                        </table>
                    </fieldset>

                    <!-- Hobbies -->
                    <#-- TODO: Implement this with freemarker -->
                    <fieldset class="box hobbies pt-2">
                        <legend class="tag is-info is-medium">Hobbies and interests</legend>
                        <div class="content">
                            <div class="columns is-multiline">
                                <div class="column is-4">
                                    <div class="has-text-centered">
                                            <span class="icon is-large">
                                                <i class="ri-book-open-line"></i>
                                            </span>
                                        <p class="is-size-6">Reading</p>
                                    </div>
                                </div>
                                <div class="column is-4">
                                    <div class="has-text-centered">
                                            <span class="icon is-large">
                                                <i class="ri-music-fill"></i>
                                            </span>
                                        <p class="is-size-6">Playing the Piano</p>
                                    </div>
                                </div>

                                <div class="column is-4">
                                    <div class="has-text-centered">
                                            <span class="icon is-large">
                                                <i class="ri-football-fill"></i>
                                            </span>
                                        <p class="is-size-6">Sports</p>
                                    </div>
                                </div>
                                <div class="column is-4">
                                    <div class="has-text-centered">
                                            <span class="icon is-large">
                                                <i class="ri-home-8-line"></i>
                                            </span>
                                        <p class="is-size-6">DIY (Do It Yourself)</p>
                                    </div>
                                </div>
                                <div class="column is-4">
                                    <div class="has-text-centered">
                                            <span class="icon is-large">
                                                <i class="ri-suitcase-2-line"></i>
                                            </span>
                                        <p class="is-size-6">Travel</p>
                                    </div>
                                </div>
                                <!-- Add more columns for additional hobbies or interests -->
                            </div>
                        </div>
                    </fieldset>

                </div>

                <!-- second part -->
                <div class="column">

                    <!-- Personal Infos -->
                    <fieldset class="box personal-info pt-1">
                        <legend class="legend is-size-3">${user.firstName} ${user.lastName}</legend>
                        <div class="columns is-2">
                            <div class="column">
                                <p class="column-content has-text-left">
                                    <span>Profession: ${user.profession}</span><br>
                                    <span>Titel: ${user.title}</span><br>
                                    <span>Relevant Experience: ${user.years}</span><br>
                                    <span>Number of Professional Projects: [number]</span><br>
                                    <span>Website: [your website]</span>
                                </p>
                            </div>
                            <div class="column has-text-right">
                                <p>
                                    <span>Phone: ${user.mobile}</span><br>
                                    <span>Email: ${user.email}</span><br>
                                    <#list resume.networks as network>
                                        <span>${network.referenceName}:${network.link} </span><br>
                                    </#list>
                                    <span>Location: ${user.location}</span>
                                </p>
                            </div>
                        </div>
                    </fieldset>

                    <!-- Work Experiences -->
                    <fieldset class="box work-experiences">
                        <legend class="tag is-info is-medium">Work Experiences</legend>

                        <div class="timeline">
                            <#list resume.workExperiences as workExperience>
                                <div class="timeline-item is-black is-block">
                                    <div class="timeline-marker is-primary is-image is-16x16"></div>
                                    <div class="timeline-content ">
                                        <div class="columns is-2 mb-0">
                                            <div class="column">
                                                <p class="column-content has-text-leftt">
                                                    <span class="heading"><strong>${workExperience.company}</strong></span>
                                                    <span class="heading">${workExperience.occupiedPosition}</span>
                                                </p>
                                            </div>
                                            <div class="column has-text-right">
                                                <p>${workExperience.startDate.toString()}
                                                    - ${workExperience.endDate.toString()}</p>
                                            </div>
                                        </div>
                                        <p>${workExperience.summary}</p>
                                    </div>
                                </div>
                            </#list>
                        </div>
                    </fieldset>

                    <!-- Skills -->
                    <fieldset class="box skills">
                        <legend class="tag is-info is-light is-medium">Skills</legend>
                        <div class="tags">
                            <#list resume.skills as skill>
                                <span class="tag is-primary is-light has-text-black">${skill}</span>
                            </#list>
                        </div>
                    </fieldset>

                    <!-- Tools -->
                    <fieldset class="box tools">
                        <legend class="tag is-info is-light is-medium">Tools</legend>
                        <div class="tags">
                            <#list resume.tools as tool>
                                <span class="tag is-primary is-light has-text-black">${tool}</span>
                            </#list>
                        </div>
                    </fieldset>

                    <!-- Methodologies -->
                    <fieldset class="box methodologies">
                        <legend class="tag is-info is-light is-medium">Methodology</legend>
                        <div class="tags is-small">
                            <#list resume.methodologies as methodology>
                                <span class="tag is-primary is-light has-text-black">${methodology}</span>
                            </#list>
                        </div>
                    </fieldset>

                    <!-- Trainings And Certifications  -->
                    <fieldset class="box trainings-certifications  pt-1">
                        <legend class="tag is-info is-light is-medium">Trainings and Certifications</legend>
                        <div class="content">
                            <ul>
                                <#list resume.trainingsAndCertifications as tc>
                                    <li>${tc}</li>
                                </#list>
                            </ul>
                        </div>
                    </fieldset>

                </div>

            </div>
        </div>

    </section>
</div>
</body>
</html>