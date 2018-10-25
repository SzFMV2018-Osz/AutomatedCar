#!/bin/bash
# based on https://benlimmer.com/2013/12/26/automatically-publish-javadoc-to-gh-pages-with-travis-ci/
# and https://github.com/ReadyTalk/swt-bling/blob/master/.utility/push-javadoc-to-gh-pages.sh
echo $CIRCLE_PULL_REQUEST
if [ "$CIRCLE_PULL_REQUEST" == "" ] && [ "$CIRCLE_BRANCH" == "master" ]; then

  echo -e "Publishing javadoc...\n"

  cp -R target/site/apidocs/ $HOME/javadoc-latest

  cd $HOME
  git config --global user.email "circle@ci.org"
  git config --global user.name "ci"
  git clone --quiet --branch=gh-pages https://${GH_TOKEN}@github.com/SzFMV2018-Osz/AutomatedCar-A gh-pages > /dev/null

  cd gh-pages
  git rm -rf ./javadoc
  cp -Rf $HOME/javadoc-latest ./javadoc
  git add -f .
  git commit -m "Latest javadoc on successful travis build $CIRCLE_BUILD_NUM auto-pushed to gh-pages"
  git push -fq origin gh-pages > /dev/null

  echo -e "Published Javadoc to gh-pages.\n"

fi
