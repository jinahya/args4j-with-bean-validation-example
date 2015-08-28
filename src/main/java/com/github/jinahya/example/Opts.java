/*
 * Copyright 2015 Jin Kwon &lt;onacit at gmail.com&gt;.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


package com.github.jinahya.example;


import java.util.Arrays;
import java.util.Set;
import java.util.logging.LogManager;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;


/**
 *
 * @author Jin Kwon &lt;onacit at gmail.com&gt;
 */
public class Opts {


    public static void main(final String[] args) throws CmdLineException {

        LogManager.getLogManager().reset();

        System.out.println("args: " + Arrays.toString(args));

        // parse arguments
        final Opts opts = new Opts();
        new CmdLineParser(opts).parseArgument(args);

        // validate parameters
        final ValidatorFactory factory
            = Validation.buildDefaultValidatorFactory();
        final Validator validator = factory.getValidator();
        final Set<ConstraintViolation<Opts>> violations
            = validator.validate(opts);
        if (violations.stream().map(v -> {
            System.err.println("violation: " + v);
            return v;
        }).count() > 0L) {
            return;
        }

        System.out.println("opts: " + opts);
    }


    @Override
    public String toString() {

        return super.toString()
               + "?bufferCapacity=" + bufferCapacity
               + "&bufferCount=" + bufferCount;
    }


    @Option(name = "-buffer-capacity",
            usage = "each buffer's capacity in bytes")
    @Min(1024)
    private int bufferCapacity = 65536;


    @Option(name = "-buffer-count", required = true,
            usage = "number of buffers to allocate")
    @Min(1)
    @Max(1024)
    private int bufferCount;


}

