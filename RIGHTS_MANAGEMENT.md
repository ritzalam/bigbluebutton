This document provides instructions for developers to setup their
environment and work on the upcoming BBB 1.1 (tentative release version).

## Install BBB 1.0

Make sure you have a working BBB 1.0 before you proceed with the instructions below.

## Install OpenJDK 8

```
sudo add-apt-repository ppa:openjdk-r/ppa
sudo apt-get update
sudo apt-get install openjdk-8-jdk
```

Change the default jre. Choose Java 8.

```
sudo update-alternatives --config java
```

Change the default jdk. Choose Jdk8

```
sudo update-alternatives --config javac
```

## Environment Variables

Edit `~/.profile` and change `JAVA_HOME`

```
export JAVA_HOME=/usr/lib/jvm/java-8-openjdk-amd64
```

Save the file and refresh environment vars.

```
source ~/.profile
```

## Update Development Tools

### Install The Core Development Tools

```
sudo apt-get install git-core ant
```


### Install Gradle

```
cd ~/dev/tools
wget http://services.gradle.org/distributions/gradle-2.12-bin.zip
unzip gradle-2.12-bin.zip
ln -s gradle-2.12 gradle 
```

### Install Grails

```
cd ~/dev/tools
wget https://github.com/grails/grails-core/releases/download/v2.5.2/grails-2.5.2.zip
unzip grails-2.5.2.zip
ln -s grails-2.5.2 grails
```

### Install Maven

```
cd ~/dev/tools
wget apache.parentingamerica.com//maven/maven-3/3.3.3/binaries/apache-maven-3.3.3-bin.zip
unzip apache-maven-3.3.3-bin.zip
ln -s apache-maven-3.3.3 maven
```

### Install sbt

```
cd ~/dev/tools
wget https://dl.bintray.com/sbt/native-packages/sbt/0.13.9/sbt-0.13.9.tgz
tar zxvf sbt-0.13.9.tgz
```

With the tools installed, you need to add a set of environment variables to your `.profile` to access these tools.

```
vi ~/.profile
```

Copy-and-paste the following text at bottom of `.profile`.

```

export GRAILS_HOME=$HOME/dev/tools/grails
export PATH=$PATH:$GRAILS_HOME/bin

export FLEX_HOME=$HOME/dev/tools/flex
export PATH=$PATH:$FLEX_HOME/bin

export GRADLE_HOME=$HOME/dev/tools/gradle
export PATH=$PATH:$GRADLE_HOME/bin

export SBT_HOME=$HOME/dev/tools/sbt
export PATH=$PATH:$SBT_HOME/bin 

export MAVEN_HOME=$HOME/dev/tools/maven
export PATH=$PATH:$MAVEN_HOME/bin 


```

Reload your profile to use these tools (this will happen automatically when you next login).

```
source ~/.profile
```

### Clone the repository.

```
 cd ~/dev

 git clone https://github.com/ritzalam/bigbluebutton.git
```

Checkout the development branch.

```
git checkout bbb-2x-prototyping
```


### Run tests

Run the tests to see if everything has been setup correctly.

```
 cd bigbluebutton/akka-bbb-apps

 sbt test
```

Open up the test report using your browser. It is located at `target/scalatest-reports/index.html`.

### Add new Roles.

Open up `src/main/scala/org/bigbluebutton/core/domain/User2x.scala` and define a new Role.

```
trait Role2x
case object ModeratorRole extends Role2x
case object ViewerRole extends Role2x
case object PresenterRole extends Role2x
case object GuestRole extends Role2x
case object AuthenticatedGuestRole extends Role2x
case object StenographerRole extends Role2x
case object SignLanguageInterpreterRole extends Role2x

```

Add new abilities for the new role. Open `src/main/scala/org/bigbluebutton/core/domain/Abilities2x.scala`.

```
sealed trait Abilities2x

case object CanRaiseHand extends Abilities2x
case object CanEjectUser extends Abilities2x
case object CanLockLayout extends Abilities2x
case object CanSetRecordingStatus extends Abilities2x
case object CanAssignPresenter extends Abilities2x
case object CanSharePresentation extends Abilities2x
case object CanShareCamera extends Abilities2x
case object CanUseMicrophone extends Abilities2x
case object CanPrivateChat extends Abilities2x
case object CanPublicChat extends Abilities2x
case object CanChangeLayout extends Abilities2x
case object CanDrawWhiteboard extends Abilities2x
case object CanShareDesktop extends Abilities2x
case object CanUploadPresentation extends Abilities2x
case object HasLayoutSupport extends Abilities2x
case object HasWebRtcSupport extends Abilities2x

```


Open up `src/main/scala/org/bigbluebutton/core/filters/DefaultAbilitiesFilter.scala` and define abilities for
the new role.

```

object RoleAbilities {
  val moderatorAbilities = Set(
    CanAssignPresenter,
    CanChangeLayout,
    CanEjectUser,
    CanLockLayout,
    CanPrivateChat,
    CanPublicChat)

  val presenterAbilities = Set(
    CanUploadPresentation,
    CanSharePresentation)

  val viewerAbilities = Set(
    CanShareCamera,
    CanRaiseHand,
    CanUseMicrophone)
}

```

Open `src/main/scala/org/bigbluebutton/core/filters/UsersAuthzFilter.scala` and add the new message handler
that will use the new role to filter the message or not.

Here is an example of checking if the user has the right to eject a user.

```
  abstract override def handleEjectUserFromMeeting(msg: EjectUserFromMeeting): Unit = {
    Users3x.findWithId(msg.ejectedBy, state.users.toVector) foreach { user =>

      val abilities = abilitiesFilter.calcEffectiveAbilities(
        user.roles,
        user.permissions,
        state.abilities.get.removed)

      if (abilitiesFilter.can(CanEjectUser, abilities)) {
        super.handleEjectUserFromMeeting(msg)
      } else {
        outGW.send(new DisconnectUser2x(msg.meetingId, msg.ejectedBy))
      }
    }
  }

```

Implement a unit test to test your handler. Open `src/test/scala/org/bigbluebutton/core/filters/UserAuthzFilterTests.scala`.

Here is a test for filtering the eject user message. 

We use [Mockito](http://mockito.org/) to mock out collaborator classes. You verify if the message is being handled or
rejected.

You can setup your test data by editing `src/test/scala/org/bigbluebutton/core/MeetingTestFixtures.scala`. This way you can
initialize the state of your meeting and then handle the message.

```
  it should "eject user if user has ability" in {

    val testRegUsers = new RegisteredUsers2x
    testRegUsers.add(du30RegisteredUser)
    testRegUsers.add(mdsRegisteredUser)
    testRegUsers.add(marRegisteredUser)

    val testUsers = new Users3x
    testUsers.save(du30User)
    testUsers.save(mdsUser)
    testUsers.save(marUser)

    val state: MeetingStateModel = new MeetingStateModel(piliProps,
      abilities, testRegUsers, testUsers, chats, layouts,
      polls, whiteboards, presentations, breakoutRooms, captions,
      new MeetingStatus)

    val mockOutGW = mock[OutMessageGateway]
    // Create the class under test and pass the mock to it
    val classUnderTest = new UsersHandlerFilterDummy(state, mockOutGW)

    val ejectUserMsg = new EjectUserFromMeeting(piliIntMeetingId, marIntUserId, du30IntUserId)

    // Use the class under test
    classUnderTest.handleEjectUserFromMeeting(ejectUserMsg)

    // Then verify the class under test used the mock object as expected
    // The disconnect user shouldn't be called as user has ability to eject another user
    verify(mockOutGW, never()).send(new DisconnectUser2x(ejectUserMsg.meetingId, ejectUserMsg.ejectedBy))
  }

```

If the message is not rejected by the filter, the message is forwarded to the meeting handler. The user ejected
meeting handler is located in `src/main/scala/org/bigbluebutton/core/handlers/UsersHandler2x.scala`.

Here is the handler which ejects the user from the meeting.

```
  def handleEjectUserFromMeeting(msg: EjectUserFromMeeting) {
    def removeAndEject(user: User3x): Unit = {
      // remove user from list of users
      state.users.remove(user.id)
      // remove user from registered users to prevent re-joining
      state.registeredUsers.remove(msg.userId)

      // Send message to user that he has been ejected.
      outGW.send(new UserEjectedFromMeeting(state.props.id,
        state.props.recordingProp.recorded,
        msg.userId, msg.ejectedBy))
      // Tell system to disconnect user.
      outGW.send(new DisconnectUser2x(msg.meetingId, msg.userId))
      // Tell all others that user has left the meeting.
      outGW.send(new UserLeft2x(state.props.id,
        state.props.recordingProp.recorded,
        msg.userId))
    }

    for {
      user <- Users3x.findWithId(msg.userId, state.users.toVector)
    } yield removeAndEject(user)
  }

```

Run the tests to see if the handlers have been implemented correctly.

```
cd ~/dev/bigbluebutton/akka-bbb-apps
sbt test
```


